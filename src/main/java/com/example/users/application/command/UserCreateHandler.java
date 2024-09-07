package com.example.users.application.command;

import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.domain.model.dto.AuthenticationResponse;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.port.JwtPort;
import com.example.users.domain.service.UserCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreateHandler {
    private final UserCreateService userCreateService;
    private final UserDtoMapper userDtoMapper;
    private final JwtPort jwtService;

    @Autowired
    public UserCreateHandler(UserCreateService userCreateService, UserDtoMapper userDtoMapper,JwtPort jwtService) {
        this.userCreateService = userCreateService;
        this.userDtoMapper = userDtoMapper;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse execute (UserCreateCommand createCommand){
        UserDto userRegister = userDtoMapper.toDto(userCreateService.execute(createCommand));
        return AuthenticationResponse.builder().token(jwtService.generateToken(userRegister)).build();
    }

}
