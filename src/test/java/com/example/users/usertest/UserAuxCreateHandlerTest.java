package com.example.users.usertest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.users.application.command.UserAuxCreateHandler;
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

class UserAuxCreateHandlerTest {

    @Mock
    private UserCreateService userCreateService;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private UserAuxCreateHandler userAuxCreateHandler;

    private UserCreateCommand createCommand;
    private UserDto userDto;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        createCommand = new UserCreateCommand();
        createCommand.setName("Jane");
        createCommand.setLastName("Doe");
        createCommand.setDni("87654321");
        createCommand.setTelephone("+573177722508");
        createCommand.setEmail("jane.doe@example.com");
        createCommand.setPassword("securePassword123");

        userDto = new UserDto();
        userDto.setName("Jane");
        userDto.setLastName("Doe");
        userDto.setEmail("jane.doe@example.com");
    }

    @Test
    void test_execute_whenUserCreatedSuccessfully_shouldReturnUserDto() {
        // Arrange
        when(userCreateService.execute(createCommand,Role.AUX_BODEGA)).thenReturn(user);
        when(userDtoMapper.toDto(user)).thenReturn(userDto);

        // Act
        UserDto result = userAuxCreateHandler.execute(createCommand);

        // Assert
        assertNotNull(result);
        assertEquals("Jane", result.getName());
        assertEquals("Doe", result.getLastName());
        assertEquals("jane.doe@example.com", result.getEmail());
        verify(userCreateService).execute(createCommand,Role.AUX_BODEGA);
        verify(userDtoMapper).toDto(user);
    }

    @Test
    void test_execute_whenUserCreationFails_shouldThrowException() {
        // Arrange
        when(userCreateService.execute(createCommand,Role.AUX_BODEGA)).thenThrow(new UserException("User creation failed"));

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> {
            userAuxCreateHandler.execute(createCommand);
        });

        // Assert
        assertEquals("User creation failed", exception.getErrorMessage());
        verify(userCreateService).execute(createCommand,Role.AUX_BODEGA);
    }

}
