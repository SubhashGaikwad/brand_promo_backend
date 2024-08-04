package com.techvg.eoffice.repository;

import com.techvg.eoffice.domain.Ghoshwara;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Ghoshwara entity.
 */
@Repository
public interface GhoshwaraRepository extends JpaRepository<Ghoshwara, Long>, JpaSpecificationExecutor<Ghoshwara> {
    default Optional<Ghoshwara> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Ghoshwara> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Ghoshwara> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct ghoshwara from Ghoshwara ghoshwara left join fetch ghoshwara.securityUser",
        countQuery = "select count(distinct ghoshwara) from Ghoshwara ghoshwara"
    )
    Page<Ghoshwara> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct ghoshwara from Ghoshwara ghoshwara left join fetch ghoshwara.securityUser")
    List<Ghoshwara> findAllWithToOneRelationships();

    @Query("select ghoshwara from Ghoshwara ghoshwara left join fetch ghoshwara.securityUser where ghoshwara.id =:id")
    Optional<Ghoshwara> findOneWithToOneRelationships(@Param("id") Long id);
}
