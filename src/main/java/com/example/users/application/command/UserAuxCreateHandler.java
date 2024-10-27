package com.example.users.application.command;

import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.model.entity.Role;
import com.example.users.domain.service.UserCreateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserAuxCreateHandler {
    private final UserCreateService userCreateService;
    private final UserDtoMapper userDtoMapper;
    public UserDto execute (UserCreateCommand userCreateCommand){
        return userDtoMapper.toDto(userCreateService.execute(userCreateCommand,Role.AUX_BODEGA));
    }
}
