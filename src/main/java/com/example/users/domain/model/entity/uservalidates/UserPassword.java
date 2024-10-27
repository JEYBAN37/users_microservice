package com.example.users.domain.model.entity.uservalidates;
import com.example.users.domain.model.exception.UserException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static com.example.users.domain.model.constant.UserConstant.*;


@NoArgsConstructor

@Getter
public class UserPassword {


    String password;
    private UserPassword(String password) {
        this.password = password;
    }

    public static UserPassword of (String password){
        return new UserPassword(validatePassword(password));
    }

  // At least one special character
    private static String validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new UserException(MESSAGE_MANDATORY_PASSWORD);
        }
        String passwordTrim = password.trim();
        if (passwordTrim.length() < 6) {
            throw new UserException(MESSAGE_PASSWORD_TOO_SHORT);
        }
        if (!passwordTrim.matches(PASSWORD_ALPHANUMERIC_PATTERN)) {
            throw new UserException(MESSAGE_PASSWORD_ALPHANUMERIC);
        }
        if (!passwordTrim.matches(SPECIAL_CHARACTER_PATTERN)) {
            throw new UserException(MESSAGE_PASSWORD_SPECIAL_CHARACTER);
        }
        return passwordTrim;
    }

}
