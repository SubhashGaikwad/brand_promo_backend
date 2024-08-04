package com.techvg.eoffice.service.mapper;

import com.techvg.eoffice.domain.Ghoshwara;
import com.techvg.eoffice.service.dto.GhoshwaraDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Ghoshwara} and its DTO {@link GhoshwaraDTO}.
 */
@Mapper(componentModel = "spring")
public interface GhoshwaraMapper extends EntityMapper<GhoshwaraDTO, Ghoshwara> {
    @Mapping(target = "securityUser.id", source = "securityUser.id")
    GhoshwaraDTO toDto(Ghoshwara s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract GhoshwaraDTO toDtoId(Ghoshwara ghoshwara);
}
