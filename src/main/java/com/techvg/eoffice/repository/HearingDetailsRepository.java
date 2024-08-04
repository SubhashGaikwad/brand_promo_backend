package com.techvg.eoffice.repository;

import com.techvg.eoffice.domain.HearingDetails;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data SQL repository for the HearingDetails entity.
 */
@Repository
public interface HearingDetailsRepository extends JpaRepository<HearingDetails, Long>, JpaSpecificationExecutor<HearingDetails> {
    default Optional<HearingDetails> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<HearingDetails> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<HearingDetails> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct hearingDetails from HearingDetails hearingDetails left join fetch hearingDetails.dakMaster",
        countQuery = "select count(distinct hearingDetails) from HearingDetails hearingDetails"
    )
    Page<HearingDetails> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct hearingDetails from HearingDetails hearingDetails left join fetch hearingDetails.dakMaster")
    List<HearingDetails> findAllWithToOneRelationships();

    @Query("select hearingDetails from HearingDetails hearingDetails left join fetch hearingDetails.dakMaster where hearingDetails.id =:id")
    Optional<HearingDetails> findOneWithToOneRelationships(@Param("id") Long id);
}
