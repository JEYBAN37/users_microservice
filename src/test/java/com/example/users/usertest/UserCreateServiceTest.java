package com.example.users.usertest;

import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.model.entity.Role;
import com.example.users.domain.model.entity.User;
import com.example.users.domain.model.exception.UserException;
import com.example.users.domain.port.dao.UserDao;
import com.example.users.domain.port.repository.UserRepository;
import com.example.users.domain.service.UserCreateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


class UserCreateServiceTest {

    private UserCreateService userCreateService;
    private UserRepository userRepository;
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userDao = mock(UserDao.class);
        userCreateService = new UserCreateService(userRepository, userDao);
    }

    @Test
     void testExecute_whenUserAlreadyExists_ShouldThrowException() {
        //arrange
        UserCreateCommand createCommand = new UserCreateCommand();
        createCommand.setId(1L);
        createCommand.setName("John");
        createCommand.setLastname("Doe");
        createCommand.setDni("12345678");
        createCommand.setTelephone("+573177722509");
        createCommand.setDateAge(LocalDate.of(1995, 5, 15));
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");
        createCommand.setRole(Role.ADMIN);


        when(userDao.idExist(createCommand.getId())).thenReturn(true);
//act y assert
        UserException exception = assertThrows(UserException.class, () -> {
            userCreateService.execute(createCommand);
        });

        assertEquals("User Exist", exception.getErrorMessage());
    }
    @Test
     void whenIdExist_shouldThrowUserException() {
        //arrange
        when(userDao.idExist(anyLong())).thenReturn(true);

        UserCreateCommand createCommand = new UserCreateCommand();
        createCommand.setId(1L);
        createCommand.setName("John");
        createCommand.setLastname("Doe");
        createCommand.setDni("12345678");
        createCommand.setTelephone("+573177722509");
        createCommand.setDateAge(LocalDate.of(1995, 5, 15));
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");
        createCommand.setRole(Role.ADMIN);

//act
        UserException exception = assertThrows(UserException.class, () -> {
            userCreateService.execute(createCommand);
        });

        String expectedMessage = "User Exist";
        String actualMessage = exception.getErrorMessage();
//assert
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
     void testExecute_whenNameAlreadyExists_ShouldThrowException() {
        // arrange
        UserCreateCommand createCommand = new UserCreateCommand();
        createCommand.setName("existingUser");

        when(userDao.nameExist(createCommand.getName())).thenReturn(true);
        // act
        UserException exception = assertThrows(UserException.class, () -> {
            userCreateService.execute(createCommand);
        });
        // assert
        assertEquals("User Exist", exception.getErrorMessage());
    }

    @Test
     void testExecute_RoleIsNull_ShouldThrowException() {
        // arrange
        UserCreateCommand createCommand = new UserCreateCommand();
        createCommand.setRole(null);
        // act
        UserException exception = assertThrows(UserException.class, () -> {
            userCreateService.execute(createCommand);
        });
        // assert
        assertEquals("Role Not Found", exception.getErrorMessage());
    }

    @Test
     void testExecute_whenRoleNotFound_shouldThrowException() {
        // arrange
        UserCreateCommand createCommand = new UserCreateCommand();
        createCommand.setRole(Role.ADMIN);

        when(userDao.getByRole(createCommand.getRole())).thenReturn(null);
        // act
        UserException exception = assertThrows(UserException.class, () -> {
            userCreateService.execute(createCommand);
        });
        // assert
        assertEquals("Role Inject Not Found", exception.getErrorMessage());
    }

    @Test
     void testExecute_whenValidUser_shouldCreateUser() {
        // arrange

        UserCreateCommand createCommand = new UserCreateCommand();
        createCommand.setId(1L);
        createCommand.setName("John");
        createCommand.setLastname("Doe");
        createCommand.setDni("12345678");
        createCommand.setTelephone("+573177722509");
        createCommand.setDateAge(LocalDate.of(1995, 5, 15));
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");
        createCommand.setRole(Role.ADMIN);

        Role role =  Role.ADMIN;
        when(userDao.getByRole(createCommand.getRole())).thenReturn(role);

        User user = new User();
        when(userRepository.create(any(User.class))).thenReturn(user);

        // act
        User createdUser = userCreateService.execute(createCommand);

        // assert
        assertNotNull(createdUser);
        verify(userRepository, times(1)).create(any(User.class));
    }
}
