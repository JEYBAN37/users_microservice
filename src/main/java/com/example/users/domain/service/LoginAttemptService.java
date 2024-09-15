package com.example.users.domain.service;

import com.example.users.domain.model.entity.User;
import com.example.users.domain.port.dao.UserDao;
import com.example.users.domain.port.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
public class LoginAttemptService {
    private static final int MAX_ATTEMPTS = 3;
    private static final int LOCK_TIME_DURATION = 15 * 60 * 1000;

    private final UserDao userDao;
    private final UserRepository userRepository;

    public void loginSucceeded(String email) {
        User user = userDao.getUser(email);
        if (user != null) {
            user.setFails(0);
            user.setLocked(false);
            user.setLockTime(null);
            userRepository.update(user);
        }
    }

    public void loginFailed(String email) {
        User user = userDao.getUser(email);
        if (user != null) {
            int attempts = user.getFails() + 1;
            user.setFails(attempts);

            if (attempts >= MAX_ATTEMPTS) {
                user.setLocked(true);
                user.setLockTime(new Timestamp(System.currentTimeMillis()));
            }
            userRepository.update(user);
        }
    }

    public boolean isBlocked(String email) {
        User user = userDao.getUser(email);
        if (user != null && user.isLocked()) {
            long lockTimeInMillis = user.getLockTime().getTime();
            long currentTimeInMillis = System.currentTimeMillis();

            if (currentTimeInMillis - lockTimeInMillis > LOCK_TIME_DURATION) {
                user.setLocked(false);
                user.setFails(0);
                user.setLockTime(null);
                userRepository.update(user);
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
