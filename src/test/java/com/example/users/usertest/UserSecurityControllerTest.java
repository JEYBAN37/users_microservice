package com.example.users.usertest;
import com.example.users.application.command.UserCreateHandler;
import com.example.users.application.query.UserLogin;
import com.example.users.domain.model.dto.AuthenticationRequest;
import com.example.users.domain.model.dto.AuthenticationResponse;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.model.entity.Role;
import com.example.users.infrastructure.rest.controller.UserSecurityController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserSecurityControllerTest {

    @InjectMocks
    private UserSecurityController userSecurityController;

    @Mock
    private UserCreateHandler userCreateHandler;

    @Mock
    private UserLogin userLogin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("Password123");

        AuthenticationResponse expectedResponse = new AuthenticationResponse("token123", Role.ADMIN);

        when(userLogin.execute(request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<AuthenticationResponse> response = userSecurityController.login(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(userLogin, times(1)).execute(request);
    }

    @Test
    void testRegister_Success() {
        // Arrange
        UserCreateCommand createCommand = new UserCreateCommand();
        createCommand.setEmail("test@example.com");
        createCommand.setPassword("Password123");

        AuthenticationResponse expectedResponse = new AuthenticationResponse("token123", Role.ADMIN);

        when(userCreateHandler.execute(createCommand)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<AuthenticationResponse> response = userSecurityController.register(createCommand);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(userCreateHandler, times(1)).execute(createCommand);
    }
}
