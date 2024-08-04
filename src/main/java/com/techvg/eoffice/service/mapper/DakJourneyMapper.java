package com.techvg.eoffice.service.mapper;

import com.techvg.eoffice.domain.CommentMaster;
import com.techvg.eoffice.domain.DakJourney;
import com.techvg.eoffice.domain.DakMaster;
import com.techvg.eoffice.domain.SecurityUser;
import com.techvg.eoffice.service.dto.CommentMasterDTO;
import com.techvg.eoffice.service.dto.DakJourneyDTO;
import com.techvg.eoffice.service.dto.DakMasterDTO;
import com.techvg.eoffice.service.dto.SecurityUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DakJourney} and its DTO {@link DakJourneyDTO}.
 */
@Mapper(componentModel = "spring")
public interface DakJourneyMapper extends EntityMapper<DakJourneyDTO, DakJourney> {
    @Mapping(target = "dakMaster", source = "dakMaster", qualifiedByName = "dakMasterInwardNumber")
    @Mapping(target = "securityUser", source = "securityUser", qualifiedByName = "securityUserUsername")
    @Mapping(target = "commentMaster", source = "commentMaster", qualifiedByName = "commentMasterId")
    DakJourneyDTO toDto(DakJourney s);

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

    @Named("commentMasterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommentMasterDTO toDtoCommentMasterId(CommentMaster commentMaster);
}
