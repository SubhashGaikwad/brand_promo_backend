package com.techvg.eoffice.service.mapper;

import com.techvg.eoffice.domain.CommentMaster;
import com.techvg.eoffice.domain.HearingDetails;
import com.techvg.eoffice.service.dto.CommentMasterDTO;
import com.techvg.eoffice.service.dto.HearingDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HearingDetails} and its DTO {@link HearingDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface HearingDetailsMapper extends EntityMapper<HearingDetailsDTO, HearingDetails> {
    @Mapping(source = "dakMaster.id", target = "dakMasterId")
    @Mapping(target = "securityUser.id", source = "securityUser.id")
    HearingDetailsDTO toDto(HearingDetails s);

    @Mapping(source = "dakMasterId", target = "dakMaster.id")
    public abstract HearingDetails toEntity(HearingDetailsDTO hearingDetailsDTO);

    //    @Named("dakMasterInwardNumber")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    @Mapping(target = "inwardNumber", source = "inwardNumber")
    //    DakMasterDTO toDtoDakMasterInwardNumber(DakMaster dakMaster);

    default HearingDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        HearingDetails hearingDetails = new HearingDetails();
        hearingDetails.setId(id);
        return hearingDetails;
    }
}
