package com.techvg.eoffice.repository;

import com.techvg.eoffice.domain.DakJourney;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DakJourney entity.
 */
@Repository
public interface DakJourneyRepository extends JpaRepository<DakJourney, Long>, JpaSpecificationExecutor<DakJourney> {
    default Optional<DakJourney> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DakJourney> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DakJourney> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct dakJourney from DakJourney dakJourney left join fetch dakJourney.dakMaster left join fetch dakJourney.securityUser",
        countQuery = "select count(distinct dakJourney) from DakJourney dakJourney"
    )
    Page<DakJourney> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct dakJourney from DakJourney dakJourney left join fetch dakJourney.dakMaster left join fetch dakJourney.securityUser"
    )
    List<DakJourney> findAllWithToOneRelationships();

    @Query(
        "select dakJourney from DakJourney dakJourney left join fetch dakJourney.dakMaster left join fetch dakJourney.securityUser where dakJourney.id =:id"
    )
    Optional<DakJourney> findOneWithToOneRelationships(@Param("id") Long id);
}
