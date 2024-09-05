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
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserCreateServiceTest {

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
    public void testExecute_UserAlreadyExists_ShouldThrowException() {
        UserCreateCommand createCommand = new UserCreateCommand();
        createCommand.setId(1L);
        createCommand.setName("John");
        createCommand.setLastname("Doe");
        createCommand.setDni("12345678");
        createCommand.setTelephone("+573177722509");
        createCommand.setDateAge(LocalDate.of(1995, 5, 15));
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");
        createCommand.setRole(2L);


        when(userDao.idExist(createCommand.getId())).thenReturn(true);

        UserException exception = assertThrows(UserException.class, () -> {
            userCreateService.execute(createCommand);
        });

        assertEquals("User Exist", exception.getErrorMessage());
    }
    @Test
    public void shouldThrowUserExceptionWhenIdExists() {
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
        createCommand.setRole(2L);


        UserException exception = assertThrows(UserException.class, () -> {
            userCreateService.execute(createCommand);
        });

        String expectedMessage = "User Exist";
        String actualMessage = exception.getErrorMessage();

        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    public void testExecute_NameAlreadyExists_ShouldThrowException() {
        UserCreateCommand createCommand = new UserCreateCommand();
        createCommand.setName("existingUser");

        when(userDao.nameExist(createCommand.getName())).thenReturn(true);

        UserException exception = assertThrows(UserException.class, () -> {
            userCreateService.execute(createCommand);
        });

        assertEquals("User Exist", exception.getErrorMessage());
    }

    @Test
    public void testExecute_RoleIsNull_ShouldThrowException() {
        UserCreateCommand createCommand = new UserCreateCommand();
        createCommand.setRole(null);

        UserException exception = assertThrows(UserException.class, () -> {
            userCreateService.execute(createCommand);
        });

        assertEquals("Role Not Found", exception.getErrorMessage());
    }

    @Test
    public void testExecute_RoleNotFound_ShouldThrowException() {
        UserCreateCommand createCommand = new UserCreateCommand();
        createCommand.setRole(1L);

        when(userDao.getByIdRole(createCommand.getRole())).thenReturn(null);

        UserException exception = assertThrows(UserException.class, () -> {
            userCreateService.execute(createCommand);
        });

        assertEquals("Role Inject Not Found", exception.getErrorMessage());
    }

    @Test
    public void testExecute_ValidUser_ShouldCreateUser() {
        UserCreateCommand createCommand = new UserCreateCommand();
        createCommand.setId(1L);
        createCommand.setName("John");
        createCommand.setLastname("Doe");
        createCommand.setDni("12345678");
        createCommand.setTelephone("+573177722509");
        createCommand.setDateAge(LocalDate.of(1995, 5, 15));
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");
        createCommand.setRole(1L);

        Role role = new Role();
        when(userDao.getByIdRole(createCommand.getRole())).thenReturn(role);

        User user = new User();
        when(userRepository.create(any(User.class))).thenReturn(user);

        User createdUser = userCreateService.execute(createCommand);

        assertNotNull(createdUser);
        verify(userRepository, times(1)).create(any(User.class));
    }
}
