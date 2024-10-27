package com.example.users.application.command;

import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.domain.model.dto.AuthenticationResponse;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.model.entity.Role;
import com.example.users.domain.port.JwtPort;
import com.example.users.domain.service.UserCreateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserClientCreateHandler {
    private final UserCreateService userCreateService;
    private final UserDtoMapper userDtoMapper;
    private final JwtPort jwtService;

    public AuthenticationResponse execute (UserCreateCommand createCommand){
        UserDto userRegister = userDtoMapper.toDto(userCreateService.execute(createCommand, Role.CLIENT));
        return AuthenticationResponse.builder().token(jwtService.generateToken(userRegister)).build();
    }
}
