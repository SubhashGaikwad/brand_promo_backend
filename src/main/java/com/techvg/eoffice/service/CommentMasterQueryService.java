package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.*; // for static metamodels
import com.techvg.eoffice.domain.CommentMaster;
import com.techvg.eoffice.repository.CommentMasterRepository;
import com.techvg.eoffice.service.criteria.CommentMasterCriteria;
import com.techvg.eoffice.service.dto.CommentMasterDTO;
import com.techvg.eoffice.service.mapper.CommentMasterMapper;
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
 * Service for executing complex queries for {@link CommentMaster} entities in the database.
 * The main input is a {@link CommentMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommentMasterDTO} or a {@link Page} of {@link CommentMasterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommentMasterQueryService extends QueryService<CommentMaster> {

    private final Logger log = LoggerFactory.getLogger(CommentMasterQueryService.class);

    private final CommentMasterRepository commentMasterRepository;

    private final CommentMasterMapper commentMasterMapper;

    public CommentMasterQueryService(CommentMasterRepository commentMasterRepository, CommentMasterMapper commentMasterMapper) {
        this.commentMasterRepository = commentMasterRepository;
        this.commentMasterMapper = commentMasterMapper;
    }

    /**
     * Return a {@link List} of {@link CommentMasterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommentMasterDTO> findByCriteria(CommentMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommentMaster> specification = createSpecification(criteria);
        return commentMasterMapper.toDto(commentMasterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommentMasterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommentMasterDTO> findByCriteria(CommentMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommentMaster> specification = createSpecification(criteria);
        return commentMasterRepository.findAll(specification, page).map(commentMasterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommentMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommentMaster> specification = createSpecification(criteria);
        return commentMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link CommentMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CommentMaster> createSpecification(CommentMasterCriteria criteria) {
        Specification<CommentMaster> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CommentMaster_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CommentMaster_.description));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), CommentMaster_.createdOn));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommentMaster_.createdBy));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), CommentMaster_.status));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), CommentMaster_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CommentMaster_.lastModifiedBy));
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(CommentMaster_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getDakMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDakMasterId(),
                            root -> root.join(CommentMaster_.dakMaster, JoinType.LEFT).get(DakMaster_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
