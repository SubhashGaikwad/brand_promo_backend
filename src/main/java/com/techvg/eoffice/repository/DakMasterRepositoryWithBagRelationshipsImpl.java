package com.techvg.eoffice.repository;

import com.techvg.eoffice.domain.DakMaster;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class DakMasterRepositoryWithBagRelationshipsImpl implements DakMasterRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DakMaster> fetchBagRelationships(Optional<DakMaster> dakMaster) {
        return dakMaster.map(this::fetchSecurityUsers);
    }

    @Override
    public Page<DakMaster> fetchBagRelationships(Page<DakMaster> dakMasters) {
        return new PageImpl<>(fetchBagRelationships(dakMasters.getContent()), dakMasters.getPageable(), dakMasters.getTotalElements());
    }

    @Override
    public List<DakMaster> fetchBagRelationships(List<DakMaster> dakMasters) {
        return Optional.of(dakMasters).map(this::fetchSecurityUsers).orElse(Collections.emptyList());
    }

    DakMaster fetchSecurityUsers(DakMaster result) {
        return entityManager
            .createQuery(
                "select dakMaster from DakMaster dakMaster left join fetch dakMaster.securityUsers where dakMaster is :dakMaster",
                DakMaster.class
            )
            .setParameter("dakMaster", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<DakMaster> fetchSecurityUsers(List<DakMaster> dakMasters) {
        return entityManager
            .createQuery(
                "select distinct dakMaster from DakMaster dakMaster left join fetch dakMaster.securityUsers where dakMaster in :dakMasters",
                DakMaster.class
            )
            .setParameter("dakMasters", dakMasters)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
