package com.techvg.eoffice.repository;

import com.techvg.eoffice.domain.UserAccess;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserAccess entity.
 */
@Repository
public interface UserAccessRepository extends JpaRepository<UserAccess, Long>, JpaSpecificationExecutor<UserAccess> {
    default Optional<UserAccess> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<UserAccess> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<UserAccess> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct userAccess from UserAccess userAccess left join fetch userAccess.securityUser",
        countQuery = "select count(distinct userAccess) from UserAccess userAccess"
    )
    Page<UserAccess> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct userAccess from UserAccess userAccess left join fetch userAccess.securityUser")
    List<UserAccess> findAllWithToOneRelationships();

    @Query("select userAccess from UserAccess userAccess left join fetch userAccess.securityUser where userAccess.id =:id")
    Optional<UserAccess> findOneWithToOneRelationships(@Param("id") Long id);
}
