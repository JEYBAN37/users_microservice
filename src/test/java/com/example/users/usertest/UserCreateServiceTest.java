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
        createCommand.setLastName("Doe");
        createCommand.setDni("12345678");
        createCommand.setTelephone("+573177722509");
        createCommand.setDateAge(LocalDate.of(1995, 5, 15));
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");



        when(userDao.idExist(createCommand.getId())).thenReturn(true);
//act y assert
        UserException exception = assertThrows(UserException.class, () -> {
            userCreateService.execute(createCommand,Role.ADMIN);
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
        createCommand.setLastName("Doe");
        createCommand.setDni("12345678");
        createCommand.setTelephone("+573177722509");
        createCommand.setDateAge(LocalDate.of(1995, 5, 15));
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");


//act
        UserException exception = assertThrows(UserException.class, () -> {
            userCreateService.execute(createCommand,Role.ADMIN);
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

        when(userDao.emailExist(createCommand.getEmail())).thenReturn(true);
        // act
        UserException exception = assertThrows(UserException.class, () -> {
            userCreateService.execute(createCommand,Role.ADMIN);
        });
        // assert
        assertEquals("User Exist", exception.getErrorMessage());
    }


    @Test
     void testExecute_whenValidUser_shouldCreateUser() {
        // arrange

        UserCreateCommand createCommand = new UserCreateCommand(
                1L,"John","Doe","12345678","+573177722509" ,LocalDate.of(1995, 5, 15),
                "john.doe@example.com", "securePassword123@"
        );

        Role role =  Role.ADMIN;
        User user = new User();
        when(userRepository.create(any(User.class))).thenReturn(user);

        // act
        User createdUser = userCreateService.execute(createCommand,Role.ADMIN);

        // assert
        assertNotNull(createdUser);
        verify(userRepository, times(1)).create(any(User.class));
    }
}
