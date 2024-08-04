package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.*; // for static metamodels
import com.techvg.eoffice.domain.HearingDetails;
import com.techvg.eoffice.repository.HearingDetailsRepository;
import com.techvg.eoffice.service.criteria.HearingDetailsCriteria;
import com.techvg.eoffice.service.dto.HearingDetailsDTO;
import com.techvg.eoffice.service.mapper.HearingDetailsMapper;
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
 * Service for executing complex queries for {@link HearingDetails} entities in the database.
 * The main input is a {@link HearingDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HearingDetailsDTO} or a {@link Page} of {@link HearingDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HearingDetailsQueryService extends QueryService<HearingDetails> {

    private final Logger log = LoggerFactory.getLogger(HearingDetailsQueryService.class);

    private final HearingDetailsRepository hearingDetailsRepository;

    private final HearingDetailsMapper hearingDetailsMapper;

    public HearingDetailsQueryService(HearingDetailsRepository hearingDetailsRepository, HearingDetailsMapper hearingDetailsMapper) {
        this.hearingDetailsRepository = hearingDetailsRepository;
        this.hearingDetailsMapper = hearingDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link HearingDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HearingDetailsDTO> findByCriteria(HearingDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HearingDetails> specification = createSpecification(criteria);
        return hearingDetailsMapper.toDto(hearingDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HearingDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HearingDetailsDTO> findByCriteria(HearingDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HearingDetails> specification = createSpecification(criteria);
        return hearingDetailsRepository.findAll(specification, page).map(hearingDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HearingDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HearingDetails> specification = createSpecification(criteria);
        return hearingDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link HearingDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<HearingDetails> createSpecification(HearingDetailsCriteria criteria) {
        Specification<HearingDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), HearingDetails_.id));
            }
            if (criteria.getAccuserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccuserName(), HearingDetails_.accuserName));
            }
            if (criteria.getOrderDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderDate(), HearingDetails_.orderDate));
            }
            if (criteria.getRespondentName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRespondentName(), HearingDetails_.respondentName));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), HearingDetails_.comment));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), HearingDetails_.date));
            }
            if (criteria.getTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTime(), HearingDetails_.time));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), HearingDetails_.status));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), HearingDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), HearingDetails_.lastModifiedBy));
            }
            if (criteria.getDakMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDakMasterId(),
                            root -> root.join(HearingDetails_.dakMaster, JoinType.LEFT).get(DakMaster_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
