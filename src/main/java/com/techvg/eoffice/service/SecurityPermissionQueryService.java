package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.*; // for static metamodels
import com.techvg.eoffice.domain.SecurityPermission;
import com.techvg.eoffice.repository.SecurityPermissionRepository;
import com.techvg.eoffice.service.criteria.SecurityPermissionCriteria;
import com.techvg.eoffice.service.dto.SecurityPermissionDTO;
import com.techvg.eoffice.service.mapper.SecurityPermissionMapper;
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
 * Service for executing complex queries for {@link SecurityPermission} entities in the database.
 * The main input is a {@link SecurityPermissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SecurityPermissionDTO} or a {@link Page} of {@link SecurityPermissionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SecurityPermissionQueryService extends QueryService<SecurityPermission> {

    private final Logger log = LoggerFactory.getLogger(SecurityPermissionQueryService.class);

    private final SecurityPermissionRepository securityPermissionRepository;

    private final SecurityPermissionMapper securityPermissionMapper;

    public SecurityPermissionQueryService(
        SecurityPermissionRepository securityPermissionRepository,
        SecurityPermissionMapper securityPermissionMapper
    ) {
        this.securityPermissionRepository = securityPermissionRepository;
        this.securityPermissionMapper = securityPermissionMapper;
    }

    /**
     * Return a {@link List} of {@link SecurityPermissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SecurityPermissionDTO> findByCriteria(SecurityPermissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SecurityPermission> specification = createSpecification(criteria);
        return securityPermissionMapper.toDto(securityPermissionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SecurityPermissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SecurityPermissionDTO> findByCriteria(SecurityPermissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SecurityPermission> specification = createSpecification(criteria);
        return securityPermissionRepository.findAll(specification, page).map(securityPermissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SecurityPermissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SecurityPermission> specification = createSpecification(criteria);
        return securityPermissionRepository.count(specification);
    }

    /**
     * Function to convert {@link SecurityPermissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SecurityPermission> createSpecification(SecurityPermissionCriteria criteria) {
        Specification<SecurityPermission> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SecurityPermission_.id));
            }
            if (criteria.getPermissionName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPermissionName(), SecurityPermission_.permissionName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SecurityPermission_.description));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), SecurityPermission_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SecurityPermission_.lastModifiedBy));
            }
            if (criteria.getSecurityRoleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityRoleId(),
                            root -> root.join(SecurityPermission_.securityRoles, JoinType.LEFT).get(SecurityRole_.id)
                        )
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(SecurityPermission_.securityUsers, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
