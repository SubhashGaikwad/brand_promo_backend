package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.*; // for static metamodels
import com.techvg.eoffice.domain.Ghoshwara;
import com.techvg.eoffice.repository.GhoshwaraRepository;
import com.techvg.eoffice.service.criteria.GhoshwaraCriteria;
import com.techvg.eoffice.service.dto.GhoshwaraDTO;
import com.techvg.eoffice.service.mapper.GhoshwaraMapper;
import java.time.Instant;
import java.util.Date;
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
import tech.jhipster.service.filter.InstantFilter;

/**
 * Service for executing complex queries for {@link Ghoshwara} entities in the database.
 * The main input is a {@link GhoshwaraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GhoshwaraDTO} or a {@link Page} of {@link GhoshwaraDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GhoshwaraQueryService extends QueryService<Ghoshwara> {

    private final Logger log = LoggerFactory.getLogger(GhoshwaraQueryService.class);

    private final GhoshwaraRepository ghoshwaraRepository;

    private final GhoshwaraMapper ghoshwaraMapper;

    public GhoshwaraQueryService(GhoshwaraRepository ghoshwaraRepository, GhoshwaraMapper ghoshwaraMapper) {
        this.ghoshwaraRepository = ghoshwaraRepository;
        this.ghoshwaraMapper = ghoshwaraMapper;
    }

    /**
     * Return a {@link List} of {@link GhoshwaraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GhoshwaraDTO> findByCriteria(GhoshwaraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);

        if (criteria.getSecurityUserId() != null) {
            //--------------------Add todays date in search criteria-----------
            // For today midnight instant date
            Long time = new Date().getTime();
            Date startDate = new Date(time - time % (24 * 60 * 60 * 1000));
            Instant todayMidnight = startDate.toInstant();

            // For tomorrow midnight instant date
            Date endDate = new Date(startDate.getTime() + 23 * 50 * 59 * 1000);
            Instant nextMidnight = endDate.toInstant();

            InstantFilter dateFilter = new InstantFilter();
            dateFilter.setGreaterThanOrEqual(todayMidnight);
            dateFilter.setLessThanOrEqual(nextMidnight);

            criteria.setLastModified(dateFilter);
        }
        final Specification<Ghoshwara> specification = createSpecification(criteria);
        return ghoshwaraMapper.toDto(ghoshwaraRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GhoshwaraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GhoshwaraDTO> findByCriteria(GhoshwaraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);

        //--------------------Add todays date in search criteria-----------
        // For today midnight instant date
        Long time = new Date().getTime();
        Date startDate = new Date(time - time % (24 * 60 * 60 * 1000));
        Instant todayMidnight = startDate.toInstant();

        // For tomorrow midnight instant date
        Date endDate = new Date(startDate.getTime() + 23 * 50 * 59 * 1000);
        Instant nextMidnight = endDate.toInstant();

        InstantFilter dateFilter = new InstantFilter();
        dateFilter.setGreaterThanOrEqual(todayMidnight);
        dateFilter.setLessThanOrEqual(nextMidnight);

        criteria.setLastModified(dateFilter);

        final Specification<Ghoshwara> specification = createSpecification(criteria);
        return ghoshwaraRepository.findAll(specification, page).map(ghoshwaraMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GhoshwaraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ghoshwara> specification = createSpecification(criteria);
        return ghoshwaraRepository.count(specification);
    }

    /**
     * Function to convert {@link GhoshwaraCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ghoshwara> createSpecification(GhoshwaraCriteria criteria) {
        Specification<Ghoshwara> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Ghoshwara_.id));
            }
            if (criteria.getRegisterType() != null) {
                specification = specification.and(buildSpecification(criteria.getRegisterType(), Ghoshwara_.registerType));
            }
            if (criteria.getInitialPendings() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInitialPendings(), Ghoshwara_.initialPendings));
            }
            if (criteria.getCurrentWeekInwards() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentWeekInwards(), Ghoshwara_.currentWeekInwards));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), Ghoshwara_.total));
            }
            if (criteria.getSelfGenerated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSelfGenerated(), Ghoshwara_.selfGenerated));
            }
            if (criteria.getCurrentWeekCleared() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentWeekCleared(), Ghoshwara_.currentWeekCleared));
            }
            if (criteria.getWeeklyPendings() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeeklyPendings(), Ghoshwara_.currentWeekPendings));
            }
            if (criteria.getFirstWeek() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstWeek(), Ghoshwara_.firstWeek));
            }
            if (criteria.getSecondWeek() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSecondWeek(), Ghoshwara_.secondWeek));
            }
            if (criteria.getThirdWeek() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThirdWeek(), Ghoshwara_.thirdWeek));
            }
            if (criteria.getFirstMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstMonth(), Ghoshwara_.firstMonth));
            }
            if (criteria.getSecondMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSecondMonth(), Ghoshwara_.secondMonth));
            }
            if (criteria.getThirdMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThirdMonth(), Ghoshwara_.thirdMonth));
            }
            if (criteria.getWithinSixMonths() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWithinSixMonths(), Ghoshwara_.withinSixMonths));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Ghoshwara_.date));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Ghoshwara_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Ghoshwara_.lastModifiedBy));
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(Ghoshwara_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
