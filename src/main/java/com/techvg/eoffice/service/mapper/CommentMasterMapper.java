package com.techvg.eoffice.service.mapper;

import com.techvg.eoffice.domain.CommentMaster;
import com.techvg.eoffice.domain.DakMaster;
import com.techvg.eoffice.service.dto.CommentMasterDTO;
import com.techvg.eoffice.service.dto.DakMasterDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link CommentMaster} and its DTO {@link CommentMasterDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMasterMapper extends EntityMapper<CommentMasterDTO, CommentMaster> {
    @Mapping(target = "securityUser.id", source = "securityUser.id")
    @Mapping(target = "dakMaster.id", source = "dakMaster.id")
    CommentMasterDTO toDto(CommentMaster s);

    @Mapping(target = "securityUser.id", source = "securityUser.id")
    @Mapping(target = "dakMaster.id", source = "dakMaster.id")
    CommentMaster toEntity(CommentMasterDTO commentMasterDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract CommentMasterDTO toDtoId(CommentMaster commentMaster);
}
