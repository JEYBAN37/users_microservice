package com.example.users.usertest;
import com.example.users.domain.model.entity.User;
import com.example.users.domain.port.dao.UserDao;
import com.example.users.domain.port.repository.UserRepository;
import com.example.users.domain.service.LoginAttemptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginAttemptServiceTest {

    private UserDao userDao;
    private UserRepository userRepository;
    private LoginAttemptService loginAttemptService;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDao.class);
        userRepository = mock(UserRepository.class);
        loginAttemptService = new LoginAttemptService(userDao, userRepository);
    }

    @Test
    void testLoginSucceeded() {
        String email = "test@example.com";
        User user = new User();
        user.setFails(2);
        user.setLocked(true);

        when(userDao.getUser(email)).thenReturn(user);

        loginAttemptService.loginSucceeded(email);

        assertEquals(0, user.getFails());
        assertFalse(user.isLocked());
        assertNull(user.getLockTime());
        verify(userRepository).update(user);
    }

    @Test
    void testLoginFailed() {
        String email = "test@example.com";
        User user = new User();
        user.setFails(1);
        user.setLocked(false);

        when(userDao.getUser(email)).thenReturn(user);

        loginAttemptService.loginFailed(email);

        assertEquals(2, user.getFails());
        assertFalse(user.isLocked());
        verify(userRepository).update(user);
    }

    @Test
    void testLoginFailedMaxAttempts() {
        String email = "test@example.com";
        User user = new User();
        user.setFails(2);
        user.setLocked(false);

        when(userDao.getUser(email)).thenReturn(user);

        loginAttemptService.loginFailed(email);

        assertTrue(user.isLocked());
        assertNotNull(user.getLockTime());
        verify(userRepository).update(user);
    }

    @Test
    void testIsBlockedWhenLocked() {
        String email = "test@example.com";
        User user = new User();
        user.setLocked(true);
        user.setLockTime(new Timestamp(System.currentTimeMillis()));

        when(userDao.getUser(email)).thenReturn(user);

        assertTrue(loginAttemptService.isBlocked(email));
    }

    @Test
    void testIsBlockedWhenNotLocked() {
        String email = "test@example.com";
        User user = new User();
        user.setLocked(false);

        when(userDao.getUser(email)).thenReturn(user);

        assertFalse(loginAttemptService.isBlocked(email));
    }

    @Test
    void testIsBlockedWhenLockExpired() {
        String email = "test@example.com";
        User user = new User();
        user.setLocked(true);
        user.setLockTime(new Timestamp(System.currentTimeMillis() - (16 * 60 * 1000))); // 16 minutos atrás

        when(userDao.getUser(email)).thenReturn(user);

        boolean isBlocked = loginAttemptService.isBlocked(email);

        assertFalse(isBlocked);
        assertFalse(user.isLocked());
        assertEquals(0, user.getFails());
        assertNull(user.getLockTime());
        verify(userRepository).update(user);
    }
}
