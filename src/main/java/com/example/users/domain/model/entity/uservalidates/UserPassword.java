package com.example.users.domain.model.entity.uservalidates;

import com.example.users.domain.model.exception.UserException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor

@Getter
public class UserPassword {
    private static final String MESSAGE_MANDATORY_PASSWORD = "Password is required.";
    private static final String MESSAGE_PASSWORD_TOO_SHORT = "Password must be at least 6 characters long.";
    private static final String MESSAGE_PASSWORD_ALPHANUMERIC = "Password must contain at least one letter and one number.";
    private static final String MESSAGE_PASSWORD_SPECIAL_CHARACTER = "Password must contain at least one special character.";

    private static final String PASSWORD_ALPHANUMERIC_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d).+$";  // At least one letter and one number
    private static final String SPECIAL_CHARACTER_PATTERN = ".*[^a-zA-Z0-9].*";

    String password;
    private UserPassword(String password) {
        this.password = password;
    }

    public static UserPassword of (String password){
        validatePassword(password);
        return new UserPassword(password);
    }

  // At least one special character

    private static void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new UserException(MESSAGE_MANDATORY_PASSWORD);
        }
        if (password.length() < 6) {
            throw new UserException(MESSAGE_PASSWORD_TOO_SHORT);
        }
        if (!password.matches(PASSWORD_ALPHANUMERIC_PATTERN)) {
            throw new UserException(MESSAGE_PASSWORD_ALPHANUMERIC);
        }
        if (!password.matches(SPECIAL_CHARACTER_PATTERN)) {
            throw new UserException(MESSAGE_PASSWORD_SPECIAL_CHARACTER);
        }
    }

}
