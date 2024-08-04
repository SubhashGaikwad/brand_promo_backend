package com.techvg.eoffice.repository;

import com.techvg.eoffice.domain.DakIdGenerator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DakIdGenerator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DakIdGeneratorRepository extends JpaRepository<DakIdGenerator, Long>, JpaSpecificationExecutor<DakIdGenerator> {}
