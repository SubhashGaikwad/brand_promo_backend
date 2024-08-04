package com.techvg.eoffice.repository;

import com.techvg.eoffice.domain.DakHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DakHistory entity.
 */
@Repository
public interface DakHistoryRepository extends JpaRepository<DakHistory, Long>, JpaSpecificationExecutor<DakHistory> {
    default Optional<DakHistory> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DakHistory> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DakHistory> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct dakHistory from DakHistory dakHistory left join fetch dakHistory.dakMaster left join fetch dakHistory.securityUser",
        countQuery = "select count(distinct dakHistory) from DakHistory dakHistory"
    )
    Page<DakHistory> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct dakHistory from DakHistory dakHistory left join fetch dakHistory.dakMaster left join fetch dakHistory.securityUser"
    )
    List<DakHistory> findAllWithToOneRelationships();

    @Query(
        "select dakHistory from DakHistory dakHistory left join fetch dakHistory.dakMaster left join fetch dakHistory.securityUser where dakHistory.id =:id"
    )
    Optional<DakHistory> findOneWithToOneRelationships(@Param("id") Long id);
}
