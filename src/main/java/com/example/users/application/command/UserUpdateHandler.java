package com.example.users.application.command;


import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.dto.command.UserEditCommand;
import com.example.users.domain.service.UserUpdateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserUpdateHandler {
    private final UserUpdateService userUpdateService;
    private final UserDtoMapper userDtoMapper;

    public UserDto execute (UserEditCommand userEditCommand, Long id){
        return userDtoMapper.toDto(userUpdateService.execute(id,userEditCommand));
    }
}

