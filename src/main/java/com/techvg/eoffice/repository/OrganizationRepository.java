package com.techvg.eoffice.repository;

import com.techvg.eoffice.domain.Organization;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Organization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {
    @Transactional
    @Modifying
    @Query(
        value = "INSERT INTO dak_id_generator(next_val_inward, next_val_outward, organization_id) VALUES (:next_val_inward, :next_val_outward, :organizationId)",
        nativeQuery = true
    )
    public int updateDakIdGenrator(
        @Param("next_val_inward") int next_val_inward,
        @Param("next_val_outward") int next_val_outward,
        @Param("organizationId") Long organizationId
    );

    @Transactional
    @Modifying
    @Query(
        value = "UPDATE dak_id_generator SET dak_id_generator.next_val_inward=:next_val_inward, dak_id_generator.next_val_outward=:next_val_outward where dak_id_generator.organization_id =:organizationId",
        nativeQuery = true
    )
    public int resetNextVal(
        @Param("next_val_inward") int next_val_inward,
        @Param("next_val_outward") int next_val_outward,
        @Param("organizationId") Long organizationId
    );
}
