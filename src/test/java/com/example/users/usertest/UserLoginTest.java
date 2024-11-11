package com.example.users.usertest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.application.command.UserLogin;
import com.example.users.domain.model.dto.AuthenticationRequest;
import com.example.users.domain.model.dto.UserDto;

import com.example.users.domain.model.entity.User;
import com.example.users.domain.port.JwtPort;
import com.example.users.domain.port.dao.UserDao;
import com.example.users.domain.service.LoginAttemptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

class UserLoginTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDao userDao;

    @Mock
    private JwtPort jwtService;

    @Mock
    private UserDtoMapper userDtoMapper;

    @Mock
    private LoginAttemptService loginAttemptService;

    @InjectMocks
    private UserLogin userLogin;

    private AuthenticationRequest request;
    private UserDto userDto;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        request = new AuthenticationRequest();
        request.setEmail("john.doe@example.com");
        request.setPassword("securePassword123");

        userDto = new UserDto();
        userDto.setEmail("john.doe@example.com");
    }


    @Test
    void test_execute_whenAuthenticationFails_shouldThrowException() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Authentication failed") {});

        // Act & Assert
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            userLogin.execute(request);
        });

        // Assert
        assertEquals("Authentication failed", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDao, never()).getUser(anyString());
    }

}
