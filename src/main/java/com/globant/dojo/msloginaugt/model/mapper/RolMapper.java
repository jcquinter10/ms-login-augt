package com.globant.dojo.msloginaugt.model.mapper;

import com.globant.dojo.msloginaugt.model.dto.login.RolDto;
import com.globant.dojo.msloginaugt.model.persistence.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RolMapper {

    @Named("toDto")
    RolDto toDto(Role role);

    @Mapping(target = "users", ignore = true)
    @Named("toEntity")
    Role toEntity(RolDto rolDto);
}
