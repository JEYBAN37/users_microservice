package com.example.users.domain.service;

import com.example.users.domain.model.entity.User;
import com.example.users.domain.port.dao.UserDao;
import com.example.users.domain.port.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

import static com.example.users.domain.model.constant.UserConstant.LOCK_TIME_DURATION;
import static com.example.users.domain.model.constant.UserConstant.MAX_ATTEMPTS;

@AllArgsConstructor
public class LoginAttemptService {

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

            long timeElapsed = System.currentTimeMillis() - user.getLockTime().getTime();


            if (timeElapsed > LOCK_TIME_DURATION) {
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
