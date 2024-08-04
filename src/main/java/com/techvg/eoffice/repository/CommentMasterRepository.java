package com.techvg.eoffice.repository;

import com.techvg.eoffice.domain.CommentMaster;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CommentMaster entity.
 */
@Repository
public interface CommentMasterRepository extends JpaRepository<CommentMaster, Long>, JpaSpecificationExecutor<CommentMaster> {
    default Optional<CommentMaster> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CommentMaster> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CommentMaster> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct commentMaster from CommentMaster commentMaster left join fetch commentMaster.securityUser left join fetch commentMaster.dakMaster",
        countQuery = "select count(distinct commentMaster) from CommentMaster commentMaster"
    )
    Page<CommentMaster> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct commentMaster from CommentMaster commentMaster left join fetch commentMaster.securityUser left join fetch commentMaster.dakMaster"
    )
    List<CommentMaster> findAllWithToOneRelationships();

    @Query(
        "select commentMaster from CommentMaster commentMaster left join fetch commentMaster.securityUser left join fetch commentMaster.dakMaster where commentMaster.id =:id"
    )
    Optional<CommentMaster> findOneWithToOneRelationships(@Param("id") Long id);
}
