package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.*; // for static metamodels
import com.techvg.eoffice.domain.SecurityUser;
import com.techvg.eoffice.repository.SecurityUserRepository;
import com.techvg.eoffice.service.criteria.SecurityUserCriteria;
import com.techvg.eoffice.service.dto.SecurityUserDTO;
import com.techvg.eoffice.service.mapper.SecurityUserMapper;
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
 * Service for executing complex queries for {@link SecurityUser} entities in the database.
 * The main input is a {@link SecurityUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SecurityUserDTO} or a {@link Page} of {@link SecurityUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SecurityUserQueryService extends QueryService<SecurityUser> {

    private final Logger log = LoggerFactory.getLogger(SecurityUserQueryService.class);

    private final SecurityUserRepository securityUserRepository;

    private final SecurityUserMapper securityUserMapper;

    public SecurityUserQueryService(SecurityUserRepository securityUserRepository, SecurityUserMapper securityUserMapper) {
        this.securityUserRepository = securityUserRepository;
        this.securityUserMapper = securityUserMapper;
    }

    /**
     * Return a {@link List} of {@link SecurityUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SecurityUserDTO> findByCriteria(SecurityUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SecurityUser> specification = createSpecification(criteria);
        return securityUserMapper.toDto(securityUserRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SecurityUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SecurityUserDTO> findByCriteria(SecurityUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SecurityUser> specification = createSpecification(criteria);
        return securityUserRepository.findAll(specification, page).map(securityUserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SecurityUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SecurityUser> specification = createSpecification(criteria);
        return securityUserRepository.count(specification);
    }

    /**
     * Function to convert {@link SecurityUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SecurityUser> createSpecification(SecurityUserCriteria criteria) {
        Specification<SecurityUser> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SecurityUser_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), SecurityUser_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), SecurityUser_.lastName));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), SecurityUser_.designation));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), SecurityUser_.username));
            }
            if (criteria.getPasswordHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPasswordHash(), SecurityUser_.passwordHash));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), SecurityUser_.email));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SecurityUser_.description));
            }
            if (criteria.getDepartment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepartment(), SecurityUser_.department));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), SecurityUser_.imageUrl));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), SecurityUser_.activated));
            }
            if (criteria.getLangKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLangKey(), SecurityUser_.langKey));
            }
            if (criteria.getActivationKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivationKey(), SecurityUser_.activationKey));
            }
            if (criteria.getResetKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResetKey(), SecurityUser_.resetKey));
            }
            if (criteria.getResetDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResetDate(), SecurityUser_.resetDate));
            }
            if (criteria.getMobileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNo(), SecurityUser_.mobileNo));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SecurityUser_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SecurityUser_.createdOn));
            }
            if (criteria.getOrganizationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganizationId(),
                            root -> root.join(SecurityUser_.organization, JoinType.LEFT).get(Organization_.id)
                        )
                    );
            }
            if (criteria.getSecurityPermissionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityPermissionId(),
                            root -> root.join(SecurityUser_.securityPermissions, JoinType.LEFT).get(SecurityPermission_.id)
                        )
                    );
            }
            if (criteria.getSecurityRoleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityRoleId(),
                            root -> root.join(SecurityUser_.securityRoles, JoinType.LEFT).get(SecurityRole_.id)
                        )
                    );
            }
            //            if (criteria.getDakMasterId() != null) {
            //                specification =
            //                    specification.and(
            //                        buildSpecification(
            //                            criteria.getDakMasterId(),
            //                            root -> root.join(SecurityUser_.dakMasters, JoinType.LEFT).get(DakMaster_.id)
            //                        )
            //                    );
            //            }
        }
        return specification;
    }
}
