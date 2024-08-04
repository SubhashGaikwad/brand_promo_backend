package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.*; // for static metamodels
import com.techvg.eoffice.domain.DakHistory;
import com.techvg.eoffice.repository.DakHistoryRepository;
import com.techvg.eoffice.service.criteria.DakHistoryCriteria;
import com.techvg.eoffice.service.dto.DakHistoryDTO;
import com.techvg.eoffice.service.mapper.DakHistoryMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link DakHistory} entities in the database.
 * The main input is a {@link DakHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DakHistoryDTO} or a {@link Page} of {@link DakHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DakHistoryQueryService extends QueryService<DakHistory> {

    private final Logger log = LoggerFactory.getLogger(DakHistoryQueryService.class);

    private final DakHistoryRepository dakHistoryRepository;

    private final DakHistoryMapper dakHistoryMapper;

    public DakHistoryQueryService(DakHistoryRepository dakHistoryRepository, DakHistoryMapper dakHistoryMapper) {
        this.dakHistoryRepository = dakHistoryRepository;
        this.dakHistoryMapper = dakHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link DakHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DakHistoryDTO> findByCriteria(DakHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DakHistory> specification = createSpecification(criteria);
        return dakHistoryMapper.toDto(dakHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DakHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DakHistoryDTO> findByCriteria(DakHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DakHistory> specification = createSpecification(criteria);
        return dakHistoryRepository.findAll(specification, page).map(dakHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DakHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DakHistory> specification = createSpecification(criteria);
        return dakHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link DakHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DakHistory> createSpecification(DakHistoryCriteria criteria) {
        Specification<DakHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DakHistory_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), DakHistory_.date));
            }
            if (criteria.getAssignedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssignedBy(), DakHistory_.assignedBy));
            }
            if (criteria.getAssignedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssignedOn(), DakHistory_.assignedOn));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), DakHistory_.createdOn));
            }
            if (criteria.getDakStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getDakStatus(), DakHistory_.dakStatus));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), DakHistory_.status));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), DakHistory_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), DakHistory_.lastModifiedBy));
            }
            if (criteria.getDakMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDakMasterId(),
                            root -> root.join(DakHistory_.dakMaster, JoinType.LEFT).get(DakMaster_.id)
                        )
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(DakHistory_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
