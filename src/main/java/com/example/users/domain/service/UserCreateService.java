package com.example.users.domain.service;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.model.entity.User;
import com.example.users.domain.model.exception.UserException;
import com.example.users.domain.port.dao.UserDao;
import com.example.users.domain.port.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserCreateService {
    private final UserRepository userRepository;
    private final UserDao userDao;

    private static final String MESSAGE_ERROR_ADD = "User Exist";
    private static final String MESSAGE_ERROR_ROLE = "Role Not Found";
    private static final String MESSAGE_ERROR_ROLE_NOT = "Role Inject Not Found";

    public User execute (UserCreateCommand createCommand){
        validateParams(createCommand);

        User userToCreate = new User().requestToCreate(
                createCommand
        );
        return userRepository.create(userToCreate);
    }

private void validateParams (UserCreateCommand createCommand){

    if (createCommand.getId() != null && userDao.idExist(createCommand.getId())) {
        throw new UserException(MESSAGE_ERROR_ADD);
    }
    if (userDao.emailExist(createCommand.getEmail()))
        throw new UserException(MESSAGE_ERROR_ADD);

    if (createCommand.getRole()== null)
        throw new UserException(MESSAGE_ERROR_ROLE);
}


}
