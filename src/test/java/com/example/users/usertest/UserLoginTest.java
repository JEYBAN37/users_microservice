package com.example.users.usertest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.application.query.UserLogin;
import com.example.users.domain.model.dto.AuthenticationRequest;
import com.example.users.domain.model.dto.AuthenticationResponse;
import com.example.users.domain.model.dto.UserDto;

import com.example.users.domain.model.entity.User;
import com.example.users.domain.model.exception.UserException;
import com.example.users.domain.port.JwtPort;
import com.example.users.domain.port.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @InjectMocks
    private UserLogin userLogin;

    private AuthenticationRequest request;
    private UserDto userDto;
    private User user;

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
    void test_execute_whenLoginIsSuccessful_shouldReturnAuthenticationResponse() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));
        when(userDao.getUser(request.getEmail())).thenReturn(user);
        when(userDtoMapper.toDto(user)).thenReturn(userDto);
        when(jwtService.generate(userDto)).thenReturn("jwtToken123");

        // Act
        AuthenticationResponse response = userLogin.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken123", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDao).getUser(request.getEmail());
        verify(userDtoMapper).toDto(user);
        verify(jwtService).generate(userDto);
    }

    @Test
    void test_execute_whenAuthenticationFails_shouldThrowException() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new AuthenticationException("Authentication failed") {});

        // Act & Assert
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            userLogin.execute(request);
        });

        // Assert
        assertEquals("Authentication failed", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDao, never()).getUser(anyString()); // Ensure that userDao.getUser is not called
    }

}
