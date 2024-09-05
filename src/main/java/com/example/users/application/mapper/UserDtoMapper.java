package com.example.users.application.mapper;

import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "dni", target = "dni")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "dateAge", target = "dateAge")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "role", target = "role")
    UserDto toDto (User objectOfDomain);
}
