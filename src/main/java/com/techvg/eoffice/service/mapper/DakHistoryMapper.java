package com.techvg.eoffice.service.mapper;

import com.techvg.eoffice.domain.DakHistory;
import com.techvg.eoffice.domain.DakMaster;
import com.techvg.eoffice.domain.SecurityUser;
import com.techvg.eoffice.service.dto.DakHistoryDTO;
import com.techvg.eoffice.service.dto.DakMasterDTO;
import com.techvg.eoffice.service.dto.SecurityUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DakHistory} and its DTO {@link DakHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface DakHistoryMapper extends EntityMapper<DakHistoryDTO, DakHistory> {
    @Mapping(target = "dakMaster", source = "dakMaster", qualifiedByName = "dakMasterInwardNumber")
    @Mapping(target = "securityUser", source = "securityUser", qualifiedByName = "securityUserUsername")
    DakHistoryDTO toDto(DakHistory s);

    @Named("dakMasterInwardNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "inwardNumber", source = "inwardNumber")
    DakMasterDTO toDtoDakMasterInwardNumber(DakMaster dakMaster);

    @Named("securityUserUsername")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    SecurityUserDTO toDtoSecurityUserUsername(SecurityUser securityUser);
}
