package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.*; // for static metamodels
import com.techvg.eoffice.domain.DakJourney;
import com.techvg.eoffice.repository.DakJourneyRepository;
import com.techvg.eoffice.service.criteria.DakJourneyCriteria;
import com.techvg.eoffice.service.dto.DakJourneyDTO;
import com.techvg.eoffice.service.mapper.DakJourneyMapper;
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
 * Service for executing complex queries for {@link DakJourney} entities in the database.
 * The main input is a {@link DakJourneyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DakJourneyDTO} or a {@link Page} of {@link DakJourneyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DakJourneyQueryService extends QueryService<DakJourney> {

    private final Logger log = LoggerFactory.getLogger(DakJourneyQueryService.class);

    private final DakJourneyRepository dakJourneyRepository;

    private final DakJourneyMapper dakJourneyMapper;

    public DakJourneyQueryService(DakJourneyRepository dakJourneyRepository, DakJourneyMapper dakJourneyMapper) {
        this.dakJourneyRepository = dakJourneyRepository;
        this.dakJourneyMapper = dakJourneyMapper;
    }

    /**
     * Return a {@link List} of {@link DakJourneyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DakJourneyDTO> findByCriteria(DakJourneyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DakJourney> specification = createSpecification(criteria);
        return dakJourneyMapper.toDto(dakJourneyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DakJourneyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DakJourneyDTO> findByCriteria(DakJourneyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DakJourney> specification = createSpecification(criteria);
        return dakJourneyRepository.findAll(specification, page).map(dakJourneyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DakJourneyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DakJourney> specification = createSpecification(criteria);
        return dakJourneyRepository.count(specification);
    }

    /**
     * Function to convert {@link DakJourneyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DakJourney> createSpecification(DakJourneyCriteria criteria) {
        Specification<DakJourney> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DakJourney_.id));
            }
            if (criteria.getAssignedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssignedOn(), DakJourney_.assignedOn));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), DakJourney_.updatedOn));
            }
            if (criteria.getDakStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getDakStatus(), DakJourney_.dakStatus));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), DakJourney_.status));
            }
            if (criteria.getDakAssignedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDakAssignedBy(), DakJourney_.dakAssignedBy));
            }
            if (criteria.getDakAssignedTo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDakAssignedTo(), DakJourney_.dakAssignedTo));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), DakJourney_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), DakJourney_.lastModifiedBy));
            }
            if (criteria.getDakMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDakMasterId(),
                            root -> root.join(DakJourney_.dakMaster, JoinType.LEFT).get(DakMaster_.id)
                        )
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(DakJourney_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getCommentMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCommentMasterId(),
                            root -> root.join(DakJourney_.commentMaster, JoinType.LEFT).get(CommentMaster_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
