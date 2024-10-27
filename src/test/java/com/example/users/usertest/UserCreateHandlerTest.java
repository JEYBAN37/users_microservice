package com.example.users.usertest;

import com.example.users.application.command.UserCreateHandler;
import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.domain.model.dto.AuthenticationResponse;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.model.entity.Role;
import com.example.users.domain.model.entity.User;
import com.example.users.domain.model.exception.UserException;
import com.example.users.domain.port.JwtPort;
import com.example.users.domain.service.UserCreateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCreateHandlerTest {

    @Mock
    private UserCreateService userCreateService;

    @Mock
    private UserDtoMapper userDtoMapper;

    @Mock
    private JwtPort jwtService;

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
        createCommand.setLastName("Doe");
        createCommand.setDni("12345678");
        createCommand.setTelephone("+573177722509");
        createCommand.setDateAge(LocalDate.of(1995, 5, 15));
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");

        user = new User();
        userDto = new UserDto();
    }

    // Test positivo: Usuario creado correctamente
    @Test
    void test_whenCreateAndReturnUserDto_shouldReturnAuthenticationResponse() {
        // Arrange
        when(userCreateService.execute(createCommand,Role.ADMIN)).thenReturn(user);
        when(userDtoMapper.toDto(user)).thenReturn(userDto);
        when(jwtService.generateToken(userDto)).thenReturn("jwt_token");

        // Act
        AuthenticationResponse result = userCreateHandler.execute(createCommand);

        // Assert
        assertNotNull(result);
        assertEquals("jwt_token", result.getToken());
        verify(userCreateService).execute(createCommand,Role.ADMIN);
        verify(userDtoMapper).toDto(user);
        verify(jwtService).generateToken(userDto);
    }

    // Test negativo: Usuario ya existe (lanzar excepción)
    @Test
    void test_whenUserAlreadyExists_shouldThrowUserException() {
        // Arrange
        when(userCreateService.execute(createCommand,Role.ADMIN)).thenThrow(new UserException("User already exists"));

        // Act & Assert
        UserException exception = assertThrows(UserException.class, () -> {
            userCreateHandler.execute(createCommand);
        });
        assertEquals("User already exists", exception.getErrorMessage());
        verify(userCreateService).execute(createCommand,Role.ADMIN);
        verifyNoMoreInteractions(jwtService); // El jwtService no debe ser llamado si falla la creación
    }

    // Test negativo: Error al generar el token
    @Test
    void test_whenTokenGenerationFails_shouldThrowException() {
        // Arrange
        when(userCreateService.execute(createCommand,Role.ADMIN)).thenReturn(user);
        when(userDtoMapper.toDto(user)).thenReturn(userDto);
        when(jwtService.generateToken(userDto)).thenThrow(new RuntimeException("Token generation failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userCreateHandler.execute(createCommand);
        });
        assertEquals("Token generation failed", exception.getMessage());
        verify(userCreateService).execute(createCommand,Role.ADMIN);
        verify(jwtService).generateToken(userDto);
    }

    // Test negativo: Error durante el mapeo de User a UserDto
    @Test
    void test_whenUserDtoMappingFails_shouldThrowException() {
        // Arrange
        when(userCreateService.execute(createCommand,Role.ADMIN)).thenReturn(user);
        when(userDtoMapper.toDto(user)).thenThrow(new RuntimeException("UserDto mapping failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userCreateHandler.execute(createCommand);
        });
        assertEquals("UserDto mapping failed", exception.getMessage());
        verify(userCreateService).execute(createCommand,Role.ADMIN);
        verify(userDtoMapper).toDto(user);
        verifyNoMoreInteractions(jwtService); // No debe llamar a jwtService si falla el mapeo
    }

    // Test positivo: Usuario creado con rol diferente
    @Test
    void test_whenUserCreatedWithDifferentRole_shouldReturnAuthenticationResponse() {
        // Arrange// Cambiar el rol
        when(userCreateService.execute(createCommand,Role.ADMIN)).thenReturn(user);
        when(userDtoMapper.toDto(user)).thenReturn(userDto);
        when(jwtService.generateToken(userDto)).thenReturn("jwt_token_user");

        // Act
        AuthenticationResponse result = userCreateHandler.execute(createCommand);

        // Assert
        assertNotNull(result);
        assertEquals("jwt_token_user", result.getToken());
        verify(userCreateService).execute(createCommand,Role.ADMIN);
        verify(userDtoMapper).toDto(user);
        verify(jwtService).generateToken(userDto);
    }

}
