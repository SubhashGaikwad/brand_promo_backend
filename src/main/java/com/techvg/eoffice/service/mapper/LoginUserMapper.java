package com.techvg.eoffice.service.mapper;

import com.techvg.eoffice.domain.Organization;
import com.techvg.eoffice.domain.SecurityRole;
import com.techvg.eoffice.domain.SecurityUser;
import com.techvg.eoffice.service.dto.LoginUserDTO;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for the entity {@link SecurityUser} and its DTO {@link LoginUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LoginUserMapper extends EntityMapper<LoginUserDTO, SecurityUser> {
    @Mapping(source = "userAccess", target = "userAccess")
    @Mapping(source = "organization", target = "organization")
    LoginUserDTO toDto(SecurityUser securityUser);

    @Mapping(target = "removeSecurityPermission", ignore = true)
    @Mapping(target = "removeSecurityRole", ignore = true)
    @Mapping(source = "organization", target = "organization")
    SecurityUser toEntity(LoginUserDTO loginUserDTO);

    default SecurityUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        SecurityUser securityUser = new SecurityUser();
        securityUser.setId(id);
        return securityUser;
    }

    @AfterMapping
    default void populateAuthorities(SecurityUser entity, @MappingTarget LoginUserDTO dto) {
        Set<String> authorities = new HashSet<>();
        Set<String> authorities1 = new HashSet<>();

        authorities =
            entity
                .getSecurityPermissions()
                .stream()
                .map(permission -> {
                    return permission.getPermissionName();
                })
                .collect(Collectors.toSet());

        for (SecurityRole role : entity.getSecurityRoles()) {
            authorities1 =
                role
                    .getSecurityPermissions()
                    .stream()
                    .map(permission -> {
                        return permission.getPermissionName();
                    })
                    .collect(Collectors.toSet());
            authorities.addAll(authorities1);
        }
        dto.setAuthorities(authorities);
    }

    @AfterMapping
    default void populateRoles(SecurityUser entity, @MappingTarget LoginUserDTO dto) {
        Set<String> roles = new HashSet<>();

        roles =
            entity
                .getSecurityRoles()
                .stream()
                .map(role -> {
                    return role.getRoleName();
                })
                .collect(Collectors.toSet());

        dto.setRoles(roles);
    }
    //    @AfterMapping
    //    default void populateOrganization(SecurityUser entity, @MappingTarget LoginUserDTO dto) {
    //        Organization org = new Organization();
    //
    //        org = entity.getOrganization();
    //
    //        dto.setOrganization(org);
    //    }
}
