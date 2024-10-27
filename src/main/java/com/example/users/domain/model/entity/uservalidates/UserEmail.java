package com.example.users.domain.model.entity.uservalidates;

import com.example.users.domain.model.exception.UserException;
import lombok.Getter;
import static com.example.users.domain.model.constant.UserConstant.*;

@Getter
public class UserEmail {
    String email;

    private UserEmail (String email){
        this.email = email;
    }

    public static UserEmail of (String email){
        return new UserEmail(toValidEmail(email));
    }

    private static String toValidEmail(String email){
        if (email == null || email.isEmpty()) {
            throw new UserException(MESSAGE_MANDATORY_EMAIL);
        }

        String emailTrim = email.trim();

        long atSymbolCount = emailTrim.chars().filter(c -> c == '@').count();

        if (atSymbolCount != 1) {
            throw new UserException(MESSAGE_EMAIL_SYMBOL);
        }

        String[] parts = emailTrim.split("@");
        String username = parts[0];
        String domain = parts[1];

        if (username.isEmpty() || domain.isEmpty()) {
            throw new UserException(MESSAGE_EMAIL_INVALID);
        }

        if (!domain.contains(".")) {
            throw new UserException(MESSAGE_EMAIL_INVALID_POINT);
        }

        if (domain.startsWith(".") || domain.endsWith(".")) {
            throw new UserException(MESSAGE_EMAIL_INVALID_POINT);
        }
        return emailTrim;
    }
}
