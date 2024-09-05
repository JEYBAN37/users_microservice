package com.example.users.application.command;

import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.service.UserCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreateHandler {
    private final UserCreateService userCreateService;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    public UserCreateHandler(UserCreateService userCreateService, UserDtoMapper userDtoMapper) {
        this.userCreateService = userCreateService;
        this.userDtoMapper = userDtoMapper;
    }

    public UserDto execute (UserCreateCommand createCommand){
        return userDtoMapper.toDto(userCreateService.execute(createCommand));
    }
}
