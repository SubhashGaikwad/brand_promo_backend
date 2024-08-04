package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.*; // for static metamodels
import com.techvg.eoffice.domain.DakMaster;
import com.techvg.eoffice.repository.DakMasterRepository;
import com.techvg.eoffice.service.criteria.DakMasterCriteria;
import com.techvg.eoffice.service.dto.DakMasterDTO;
import com.techvg.eoffice.service.mapper.DakMasterMapper;
import com.techvg.eoffice.web.rest.vm.DakSearchCriteria;
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
 * Service for executing complex queries for {@link DakMaster} entities in the
 * database. The main input is a {@link DakMasterCriteria} which gets converted
 * to {@link Specification}, in a way that all the filters must apply. It
 * returns a {@link List} of {@link DakMasterDTO} or a {@link Page} of
 * {@link DakMasterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DakMasterQueryService extends QueryService<DakMaster> {

    private final Logger log = LoggerFactory.getLogger(DakMasterQueryService.class);

    private final DakMasterRepository dakMasterRepository;

    private final DakMasterMapper dakMasterMapper;

    public DakMasterQueryService(DakMasterRepository dakMasterRepository, DakMasterMapper dakMasterMapper) {
        this.dakMasterRepository = dakMasterRepository;
        this.dakMasterMapper = dakMasterMapper;
    }

    /**
     * Return a {@link List} of {@link DakMasterDTO} which matches the criteria from
     * the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DakMasterDTO> findByCriteria(DakMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DakMaster> specification = createSpecification(criteria);
        return dakMasterMapper.toDto(dakMasterRepository.findAll(specification));
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
    public Page<DakMasterDTO> findByCriteria(DakMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DakMaster> specification = createSpecification(criteria);
        Page<DakMaster> result = dakMasterRepository.findAll(specification, page);

        return result.map(dakMasterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DakMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DakMaster> specification = createSpecification(criteria);
        return dakMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link DakMasterCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DakMaster> createSpecification(DakMasterCriteria criteria) {
        Specification<DakMaster> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DakMaster_.id));
            }
            if (criteria.getInwardNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInwardNumber(), DakMaster_.inwardNumber));
            }
            if (criteria.getSenderName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSenderName(), DakMaster_.senderName));
            }
            if (criteria.getContactNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactNumber(), DakMaster_.contactNumber));
            }
            if (criteria.getSenderAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSenderAddress(), DakMaster_.senderAddress));
            }
            if (criteria.getSenderEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSenderEmail(), DakMaster_.senderEmail));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), DakMaster_.subject));
            }
            if (criteria.getLetterDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLetterDate(), DakMaster_.letterDate));
            }
            if (criteria.getCurrentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrentStatus(), DakMaster_.currentStatus));
            }
            if (criteria.getLetterStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getLetterStatus(), DakMaster_.letterStatus));
            }
            if (criteria.getLetterReceivedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLetterReceivedDate(), DakMaster_.letterReceivedDate));
            }
            if (criteria.getAwaitReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAwaitReason(), DakMaster_.awaitReason));
            }
            if (criteria.getDispatchDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDispatchDate(), DakMaster_.dispatchDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), DakMaster_.createdBy));
            }
            if (criteria.getLetterType() != null) {
                specification = specification.and(buildSpecification(criteria.getLetterType(), DakMaster_.letterType));
            }
            if (criteria.getIsResponseReceived() != null) {
                specification = specification.and(buildSpecification(criteria.getIsResponseReceived(), DakMaster_.isResponseReceived));
            }
            if (criteria.getAssignedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssignedDate(), DakMaster_.assignedDate));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), DakMaster_.updatedOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), DakMaster_.updatedBy));
            }
            // if (criteria.getDakAssignedFrom() != null) {
            // specification =
            // specification.and(buildRangeSpecification(criteria.getDakAssignedFrom(),
            // DakMaster_.dakAssignedFrom));
            // }
            // if (criteria.getDakAssignee() != null) {
            // specification =
            // specification.and(buildRangeSpecification(criteria.getDakAssignee(),
            // DakMaster_.dakAssignee));
            // }
            // if (criteria.getDispatchBy() != null) {
            // specification =
            // specification.and(buildRangeSpecification(criteria.getDispatchBy(),
            // DakMaster_.dispatchBy));
            // }
            if (criteria.getDakAssignedFrom() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDakAssignedFrom(),
                            root -> root.join(DakMaster_.dakAssignedFrom, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getDakAssignee() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDakAssignee(),
                            root -> root.join(DakMaster_.dakAssignee, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getDispatchedBy() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDispatchedBy(),
                            root -> root.join(DakMaster_.dispatchedBy, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getSenderOutward() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSenderOutward(), DakMaster_.senderOutward));
            }
            if (criteria.getOutwardNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutwardNumber(), DakMaster_.outwardNumber));
            }
            if (criteria.getTaluka() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaluka(), DakMaster_.taluka));
            }
            if (criteria.getOrganizationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganizationId(),
                            root -> root.join(DakMaster_.organization, JoinType.LEFT).get(Organization_.id)
                        )
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(DakMaster_.securityUsers, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
