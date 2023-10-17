package com.globant.dojo.msloginaugt.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.globant.dojo.msloginaugt.model.dto.login.UserDto;
import com.globant.dojo.msloginaugt.model.persistence.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Mapping(target = "firstName", source = "name", qualifiedByName = "setFirstName")
    @Mapping(target = "lastName", source = "name", qualifiedByName = "setLastName")
    @Mapping(target = "roleId", ignore = true)
    @Named("toDto")
    UserDto toDto(User user);

    @Mapping(target = "name", source = "userDto", qualifiedByName = "setName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Named("toEntity")
    User toEntity(UserDto userDto);

    @Named("setName")
    default String setName(UserDto userDto) {
        return userDto.getFirstName().concat(" ").concat(userDto.getLastName());
    }


    @Named("setFirstName")
    default String setFirstName(String name) throws JsonProcessingException {
        return name.split(" ")[0];
    }

    @Named("setLastName")
    default String setLastName(String name) throws JsonProcessingException {
        return name.split(" ")[1];
    }

}
