package com.techvg.eoffice.repository;

import com.techvg.eoffice.domain.SecurityRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SecurityRole entity.
 */
@Repository
public interface SecurityRoleRepository
    extends SecurityRoleRepositoryWithBagRelationships, JpaRepository<SecurityRole, Long>, JpaSpecificationExecutor<SecurityRole> {
    default Optional<SecurityRole> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<SecurityRole> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<SecurityRole> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
