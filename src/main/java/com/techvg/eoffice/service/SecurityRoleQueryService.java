package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.*; // for static metamodels
import com.techvg.eoffice.domain.SecurityRole;
import com.techvg.eoffice.repository.SecurityRoleRepository;
import com.techvg.eoffice.service.criteria.SecurityRoleCriteria;
import com.techvg.eoffice.service.dto.SecurityRoleDTO;
import com.techvg.eoffice.service.mapper.SecurityRoleMapper;
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
 * Service for executing complex queries for {@link SecurityRole} entities in the database.
 * The main input is a {@link SecurityRoleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SecurityRoleDTO} or a {@link Page} of {@link SecurityRoleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SecurityRoleQueryService extends QueryService<SecurityRole> {

    private final Logger log = LoggerFactory.getLogger(SecurityRoleQueryService.class);

    private final SecurityRoleRepository securityRoleRepository;

    private final SecurityRoleMapper securityRoleMapper;

    public SecurityRoleQueryService(SecurityRoleRepository securityRoleRepository, SecurityRoleMapper securityRoleMapper) {
        this.securityRoleRepository = securityRoleRepository;
        this.securityRoleMapper = securityRoleMapper;
    }

    /**
     * Return a {@link List} of {@link SecurityRoleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SecurityRoleDTO> findByCriteria(SecurityRoleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SecurityRole> specification = createSpecification(criteria);
        return securityRoleMapper.toDto(securityRoleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SecurityRoleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SecurityRoleDTO> findByCriteria(SecurityRoleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SecurityRole> specification = createSpecification(criteria);
        return securityRoleRepository.findAll(specification, page).map(securityRoleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SecurityRoleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SecurityRole> specification = createSpecification(criteria);
        return securityRoleRepository.count(specification);
    }

    /**
     * Function to convert {@link SecurityRoleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SecurityRole> createSpecification(SecurityRoleCriteria criteria) {
        Specification<SecurityRole> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SecurityRole_.id));
            }
            if (criteria.getRoleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoleName(), SecurityRole_.roleName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SecurityRole_.description));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), SecurityRole_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SecurityRole_.lastModifiedBy));
            }
            if (criteria.getSecurityPermissionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityPermissionId(),
                            root -> root.join(SecurityRole_.securityPermissions, JoinType.LEFT).get(SecurityPermission_.id)
                        )
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(SecurityRole_.securityUsers, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
