package com.techvg.eoffice.service.mapper;

import com.techvg.eoffice.domain.DakIdGenerator;
import com.techvg.eoffice.domain.Organization;
import com.techvg.eoffice.service.dto.DakIdGeneratorDTO;
import com.techvg.eoffice.service.dto.OrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DakIdGenerator} and its DTO {@link DakIdGeneratorDTO}.
 */
@Mapper(componentModel = "spring")
public interface DakIdGeneratorMapper extends EntityMapper<DakIdGeneratorDTO, DakIdGenerator> {
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationId")
    DakIdGeneratorDTO toDto(DakIdGenerator s);

    @Named("organizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationDTO toDtoOrganizationId(Organization organization);
}
