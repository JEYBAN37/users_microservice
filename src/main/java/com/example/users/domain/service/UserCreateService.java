package com.example.users.domain.service;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.model.entity.Role;
import com.example.users.domain.model.entity.User;
import com.example.users.domain.model.exception.UserException;
import com.example.users.domain.port.dao.UserDao;
import com.example.users.domain.port.repository.UserRepository;
import lombok.AllArgsConstructor;
import static com.example.users.domain.model.constant.UserConstant.MESSAGE_ERROR_ADD;

@AllArgsConstructor
public class UserCreateService {
    private final UserRepository userRepository;
    private final UserDao userDao;

    public User execute (UserCreateCommand createCommand, Role role){
        validateParams(createCommand);
        User userToCreate = new User().requestToCreate(createCommand,role);
        return userRepository.create(userToCreate);
    }


private void validateParams (UserCreateCommand createCommand) {
    if (createCommand.getId() != null && userDao.idExist(createCommand.getId())) {
        throw new UserException(MESSAGE_ERROR_ADD);
    }
    if (userDao.emailExist(createCommand.getEmail()))
        throw new UserException(MESSAGE_ERROR_ADD);
    }
}
