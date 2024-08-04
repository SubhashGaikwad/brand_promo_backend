package com.techvg.eoffice.service.mapper;

import com.techvg.eoffice.domain.DakMaster;
import com.techvg.eoffice.domain.SecurityUser;
import com.techvg.eoffice.service.dto.DakMasterDTO;
import com.techvg.eoffice.service.dto.SecurityUserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link DakMaster} and its DTO {@link DakMasterDTO}.
 */
@Mapper(componentModel = "spring")
public interface DakMasterMapper extends EntityMapper<DakMasterDTO, DakMaster> {
    @Mapping(target = "organization.id", source = "organization.id")
    @Mapping(target = "securityUsers.id", source = "securityUsers.id")
    @Mapping(target = "dispatchedBy.id", source = "dispatchedBy.id")
    @Mapping(target = "dakAssignee.id", source = "dakAssignee.id")
    @Mapping(source = "dakAssignedFrom.id", target = "dakAssignedFrom.id")
    DakMasterDTO toDto(DakMaster s);

    @Mapping(target = "securityUsers.id", source = "securityUsers.id")
    @Mapping(target = "dispatchedBy.id", source = "dispatchedBy.id")
    @Mapping(target = "dakAssignee.id", source = "dakAssignee.id")
    @Mapping(source = "dakAssignedFrom.id", target = "dakAssignedFrom.id")
    DakMaster toEntity(DakMasterDTO dakMasterDTO);

    //    @Named("organizationId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    OrganizationDTO toDtoOrganizationId(Organization organization);

    @Named("securityUserUsername")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    SecurityUserDTO toDtoSecurityUserUsername(SecurityUser securityUser);

    //    @Named("securityUserUsernameSet")
    //    default Set<SecurityUserDTO> toDtoSecurityUserUsernameSet(Set<SecurityUser> securityUser) {
    //        return securityUser.stream().map(this::toDtoSecurityUserUsername).collect(Collectors.toSet());
    //    }

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract DakMasterDTO toDtoId(DakMaster dakMaster);

    default DakMaster fromId(Long id) {
        if (id == null) {
            return null;
        }
        DakMaster dakMaster = new DakMaster();
        dakMaster.setId(id);
        return dakMaster;
    }
}
