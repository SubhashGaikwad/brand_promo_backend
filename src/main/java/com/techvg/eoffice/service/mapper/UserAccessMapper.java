package com.techvg.eoffice.service.mapper;

import com.techvg.eoffice.domain.SecurityUser;
import com.techvg.eoffice.domain.UserAccess;
import com.techvg.eoffice.service.dto.SecurityUserDTO;
import com.techvg.eoffice.service.dto.UserAccessDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAccess} and its DTO {@link UserAccessDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserAccessMapper extends EntityMapper<UserAccessDTO, UserAccess> {
    @Mapping(target = "securityUser", source = "securityUser", qualifiedByName = "securityUserUsername")
    UserAccessDTO toDto(UserAccess s);

    @Named("securityUserUsername")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    SecurityUserDTO toDtoSecurityUserUsername(SecurityUser securityUser);
}
