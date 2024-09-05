package com.example.users.usertest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.users.application.command.UserCreateHandler;

import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.model.entity.Role;
import com.example.users.domain.model.entity.User;
import com.example.users.domain.model.exception.UserException;

import com.example.users.domain.service.UserCreateService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

class UserCreateHandlerTest {

    @Mock
    private UserCreateService userCreateService;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private UserCreateHandler userCreateHandler;

    private UserCreateCommand createCommand;
    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        createCommand = new UserCreateCommand();
        createCommand.setName("John");
        createCommand.setLastname("Doe");
        createCommand.setDni("12345678");
        createCommand.setTelephone("+573177722509");
        createCommand.setDateAge(LocalDate.of(1995, 5, 15));
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");
        createCommand.setRole(Role.ADMIN);

        user = new User();
        userDto = new UserDto();

    }
    @Test
     void test_whenCreateAndReturnUserDto_shouldExecuteUser() {
        // arrange
        when(userCreateService.execute(createCommand)).thenReturn(user);
        when(userDtoMapper.toDto(user)).thenReturn(userDto);
        //act
        UserDto result = userCreateHandler.execute(createCommand);
        // assert
        assertNotNull(result);
        assertEquals(userDto, result);
        verify(userCreateService).execute(createCommand);
        verify(userDtoMapper).toDto(user);
    }

    @Test
     void test_whenUserCreateFails_shouldThrowException() {
        // arrange
        when(userCreateService.execute(createCommand)).thenThrow(new UserException("User Exist"));
        // act
        UserException exception = assertThrows(UserException.class, () -> {
            userCreateHandler.execute(createCommand);
        });
        // assert
        assertEquals("User Exist", exception.getErrorMessage());
    }
}
