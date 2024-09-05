package com.example.users.domain.service;

import com.example.users.domain.model.exception.UserException;
import com.example.users.domain.port.dao.UserDao;
import com.example.users.domain.port.repository.UserRepository;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class UserDeleteService {
    private final UserRepository userRepository;
    private final UserDao userDao;
    private static final String MESSAGE_ERROR_REMOVE = "Error To Remove No Exist";

    public void execute(Long id){
        if (!userDao.idExist(id))
            throw new UserException(MESSAGE_ERROR_REMOVE);
        userRepository.deleteById(id);
    }
}
