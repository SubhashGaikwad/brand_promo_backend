package com.techvg.eoffice.repository;

import com.techvg.eoffice.domain.DakMaster;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DakMasterRepositoryWithBagRelationships {
    Optional<DakMaster> fetchBagRelationships(Optional<DakMaster> dakMaster);

    List<DakMaster> fetchBagRelationships(List<DakMaster> dakMasters);

    Page<DakMaster> fetchBagRelationships(Page<DakMaster> dakMasters);
}
