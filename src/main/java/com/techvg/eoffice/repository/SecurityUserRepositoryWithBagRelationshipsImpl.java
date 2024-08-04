package com.techvg.eoffice.repository;

import com.techvg.eoffice.domain.SecurityUser;
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
public class SecurityUserRepositoryWithBagRelationshipsImpl implements SecurityUserRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SecurityUser> fetchBagRelationships(Optional<SecurityUser> securityUser) {
        return securityUser.map(this::fetchSecurityPermissions).map(this::fetchSecurityRoles);
    }

    @Override
    public Page<SecurityUser> fetchBagRelationships(Page<SecurityUser> securityUsers) {
        return new PageImpl<>(
            fetchBagRelationships(securityUsers.getContent()),
            securityUsers.getPageable(),
            securityUsers.getTotalElements()
        );
    }

    @Override
    public List<SecurityUser> fetchBagRelationships(List<SecurityUser> securityUsers) {
        return Optional.of(securityUsers).map(this::fetchSecurityPermissions).map(this::fetchSecurityRoles).orElse(Collections.emptyList());
    }

    SecurityUser fetchSecurityPermissions(SecurityUser result) {
        return entityManager
            .createQuery(
                "select securityUser from SecurityUser securityUser left join fetch securityUser.securityPermissions where securityUser is :securityUser",
                SecurityUser.class
            )
            .setParameter("securityUser", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<SecurityUser> fetchSecurityPermissions(List<SecurityUser> securityUsers) {
        return entityManager
            .createQuery(
                "select distinct securityUser from SecurityUser securityUser left join fetch securityUser.securityPermissions where securityUser in :securityUsers",
                SecurityUser.class
            )
            .setParameter("securityUsers", securityUsers)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }

    SecurityUser fetchSecurityRoles(SecurityUser result) {
        return entityManager
            .createQuery(
                "select securityUser from SecurityUser securityUser left join fetch securityUser.securityRoles where securityUser is :securityUser",
                SecurityUser.class
            )
            .setParameter("securityUser", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<SecurityUser> fetchSecurityRoles(List<SecurityUser> securityUsers) {
        return entityManager
            .createQuery(
                "select distinct securityUser from SecurityUser securityUser left join fetch securityUser.securityRoles where securityUser in :securityUsers",
                SecurityUser.class
            )
            .setParameter("securityUsers", securityUsers)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
