package com.techvg.eoffice.service.mapper;

import com.techvg.eoffice.domain.Organization;
import com.techvg.eoffice.domain.ParameterLookup;
import com.techvg.eoffice.service.dto.OrganizationDTO;
import com.techvg.eoffice.service.dto.ParameterLookupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterLookup} and its DTO {@link ParameterLookupDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParameterLookupMapper extends EntityMapper<ParameterLookupDTO, ParameterLookup> {
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationId")
    ParameterLookupDTO toDto(ParameterLookup s);

    @Named("organizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationDTO toDtoOrganizationId(Organization organization);
}
