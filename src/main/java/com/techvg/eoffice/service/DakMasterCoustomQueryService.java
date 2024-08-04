package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.DakMaster;
import com.techvg.eoffice.repository.DakMasterRepository;
import com.techvg.eoffice.service.dto.DakMasterDTO;
import com.techvg.eoffice.service.mapper.DakMasterMapper;
import com.techvg.eoffice.web.rest.vm.DakSearchCriteria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional(readOnly = true)
public class DakMasterCoustomQueryService extends QueryService<DakMaster> {

    private final Logger log = LoggerFactory.getLogger(DakMasterCoustomQueryService.class);

    private final DakMasterRepository dakMasterRepository;

    private final DakMasterMapper dakMasterMapper;

    public DakMasterCoustomQueryService(DakMasterRepository dakMasterRepository, DakMasterMapper dakMasterMapper) {
        this.dakMasterRepository = dakMasterRepository;
        this.dakMasterMapper = dakMasterMapper;
    }

    /**
     * Return a {@link Page} of {@link DakMasterDTO} which matches the criteria from
     * the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DakMasterDTO> findByCriteria(DakSearchCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        Specification<DakMaster> newDakCriteria = createDakCriteria(criteria);
        Page<DakMaster> result = dakMasterRepository.findAll(newDakCriteria, page);
        return result.map(dakMasterMapper::toDto);
    }

    protected Specification<DakMaster> createDakCriteria(DakSearchCriteria criteria) {
        return (dak, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if ((criteria.getInwardNoFrom() != null) && (criteria.getInwardNoTo() != null)) {
                predicates.add(cb.between(dak.get("inwardNumber"), criteria.getInwardNoFrom(), criteria.getInwardNoTo()));
            }
            if ((criteria.getInwardNoFrom() != null) && (criteria.getInwardNoTo() == null)) {
                predicates.add(cb.like(dak.get("inwardNumber"), "%" + criteria.getInwardNoFrom() + "%"));
            }

            if (criteria.getOrganizationId() != null) {
                Join<Object, Object> org = dak.join("organization");
                predicates.add(cb.equal(org.get("id"), criteria.getOrganizationId()));
            }

            if (criteria.getDakAssignedfromId() != null) {
                Join<Object, Object> emp = dak.join("dakAssignedFrom");
                predicates.add(cb.equal(emp.get("id"), criteria.getDakAssignedfromId()));
            }

            if (criteria.getDakAssignedToId() != null) {
                Join<Object, Object> emp = dak.join("dakAssignee");
                predicates.add(cb.equal(emp.get("id"), criteria.getDakAssignedToId()));
            }

            Predicate[] predicatesArr = predicates.toArray(new Predicate[predicates.size()]);
            return cb.and(predicatesArr);
        };
    }
}
