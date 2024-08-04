package com.techvg.eoffice.repository;

import com.techvg.eoffice.domain.DakMaster;
import com.techvg.eoffice.domain.enumeration.DakStatus;
import com.techvg.eoffice.service.dto.DakMasterDTO;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DakMaster entity.
 */
@Repository
public interface DakMasterRepository
    extends DakMasterRepositoryWithBagRelationships, JpaRepository<DakMaster, Long>, JpaSpecificationExecutor<DakMaster> {
    default Optional<DakMaster> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<DakMaster> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<DakMaster> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    @Query(
        value = "SELECT next_val_inward FROM dak_id_generator WHERE dak_id_generator.organization_id =:organizationId ",
        nativeQuery = true
    )
    int findNextDakInward(@Param("organizationId") long organizationId);

    @Modifying
    @Query(
        value = "UPDATE dak_id_generator SET dak_id_generator.next_val_inward=:nextVal where dak_id_generator.next_val_inward=:lastVal AND dak_id_generator.organization_id =:organizationId",
        nativeQuery = true
    )
    int updateNextDakInward(@Param("lastVal") int lastVal, @Param("nextVal") int nextVal, @Param("organizationId") long organizationId);

    @Query(
        value = "SELECT next_val_outward FROM dak_id_generator WHERE dak_id_generator.organization_id =:organizationId",
        nativeQuery = true
    )
    int findNextDakOutward(@Param("organizationId") Long organizationId);

    @Transactional
    @Modifying
    @Query(
        value = "UPDATE dak_id_generator SET dak_id_generator.next_val_outward=:nextOutward where dak_id_generator.next_val_outward=:lastOutward AND dak_id_generator.organization_id =:organizationId",
        nativeQuery = true
    )
    int updateNextDakOutward(
        @Param("lastOutward") int lastOutward,
        @Param("nextOutward") int nextOutward,
        @Param("organizationId") Long organizationId
    );

    @Transactional
    @Modifying
    @Query(
        value = "UPDATE dak_master SET dak_master.dak_assignee=:dakAssignee, dak_master.dak_assign_from=:dakAssignedfrom, dak_master.current_status=:updatedStatus where dak_master.id=:dakId",
        nativeQuery = true
    )
    void transferLetter(
        @Param("dakAssignedfrom") Long dakAssignedfrom,
        @Param("dakAssignee") Long dakAssignee,
        @Param("updatedStatus") String updatedStatus,
        @Param("dakId") Long dakId
    );
}
