package com.example.users.usertest;

import com.example.users.application.command.UserClientCreateHandler;
import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.domain.model.dto.AuthenticationResponse;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.model.entity.Role;
import com.example.users.domain.model.entity.User;
import com.example.users.domain.port.JwtPort;
import com.example.users.domain.service.UserCreateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserClientCreateHandlerTest {

    @Mock
    private UserCreateService userCreateService;

    @Mock
    private UserDtoMapper userDtoMapper;

    @Mock
    private JwtPort jwtService;

    @InjectMocks
    private UserClientCreateHandler userClientCreateHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_ShouldReturnAuthenticationResponse() {
        // Datos simulados
        UserCreateCommand createCommand = new UserCreateCommand();
        UserDto userDto = new UserDto();
        String expectedToken = "test-token";

        // Simula los comportamientos
        when(userCreateService.execute(any(UserCreateCommand.class), any(Role.class))).thenReturn(new User()); // Simula un objeto de retorno
        when(userDtoMapper.toDto(any())).thenReturn(userDto);
        when(jwtService.generateToken(userDto)).thenReturn(expectedToken);

        // Ejecuta el método que estamos probando
        AuthenticationResponse response = userClientCreateHandler.execute(createCommand);

        // Verifica el resultado
        assertNotNull(response);
        assertEquals(expectedToken, response.getToken());
    }

    @Test
    void testExecute_WhenUserCreateServiceFails_ShouldThrowException() {
        // Datos simulados
        UserCreateCommand createCommand = new UserCreateCommand();

        // Simula un fallo en el servicio de creación de usuarios
        when(userCreateService.execute(any(UserCreateCommand.class), any(Role.class)))
                .thenThrow(new RuntimeException("User creation failed"));

        // Verifica que se lance una excepción al ejecutar el método
        assertThrows(RuntimeException.class, () -> {
            userClientCreateHandler.execute(createCommand);
        });
    }
}
