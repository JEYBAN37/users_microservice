package com.example.users.domain.service;
import com.example.users.domain.model.dto.command.UserEditCommand;
import com.example.users.domain.model.entity.User;
import com.example.users.domain.port.dao.UserDao;
import com.example.users.domain.port.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserUpdateService {
    private final UserRepository userRepository;
    private final UserDao userDao;


    private static final String MESSAGE_ERROR_UPDATE = "User No Exist";
    private static final String MESSAGE_ERROR_ADD = "User With This Name Exist";
    private static final String MESSAGE_ERROR_BRAND_NOT = "Brand Inject Not Found";

    public User execute(Long id, UserEditCommand userEditCommand) {


        return null;
    }


}
