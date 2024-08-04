package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.*; // for static metamodels
import com.techvg.eoffice.domain.DakIdGenerator;
import com.techvg.eoffice.repository.DakIdGeneratorRepository;
import com.techvg.eoffice.service.criteria.DakIdGeneratorCriteria;
import com.techvg.eoffice.service.dto.DakIdGeneratorDTO;
import com.techvg.eoffice.service.mapper.DakIdGeneratorMapper;
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
 * Service for executing complex queries for {@link DakIdGenerator} entities in the database.
 * The main input is a {@link DakIdGeneratorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DakIdGeneratorDTO} or a {@link Page} of {@link DakIdGeneratorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DakIdGeneratorQueryService extends QueryService<DakIdGenerator> {

    private final Logger log = LoggerFactory.getLogger(DakIdGeneratorQueryService.class);

    private final DakIdGeneratorRepository dakIdGeneratorRepository;

    private final DakIdGeneratorMapper dakIdGeneratorMapper;

    public DakIdGeneratorQueryService(DakIdGeneratorRepository dakIdGeneratorRepository, DakIdGeneratorMapper dakIdGeneratorMapper) {
        this.dakIdGeneratorRepository = dakIdGeneratorRepository;
        this.dakIdGeneratorMapper = dakIdGeneratorMapper;
    }

    /**
     * Return a {@link List} of {@link DakIdGeneratorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DakIdGeneratorDTO> findByCriteria(DakIdGeneratorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DakIdGenerator> specification = createSpecification(criteria);
        return dakIdGeneratorMapper.toDto(dakIdGeneratorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DakIdGeneratorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DakIdGeneratorDTO> findByCriteria(DakIdGeneratorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DakIdGenerator> specification = createSpecification(criteria);
        return dakIdGeneratorRepository.findAll(specification, page).map(dakIdGeneratorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DakIdGeneratorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DakIdGenerator> specification = createSpecification(criteria);
        return dakIdGeneratorRepository.count(specification);
    }

    /**
     * Function to convert {@link DakIdGeneratorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DakIdGenerator> createSpecification(DakIdGeneratorCriteria criteria) {
        Specification<DakIdGenerator> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DakIdGenerator_.id));
            }
            if (criteria.getNextValInward() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextValInward(), DakIdGenerator_.nextValInward));
            }
            if (criteria.getNextValOutward() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextValOutward(), DakIdGenerator_.nextValOutward));
            }
            if (criteria.getOrganizationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganizationId(),
                            root -> root.join(DakIdGenerator_.organization, JoinType.LEFT).get(Organization_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
