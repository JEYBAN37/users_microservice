package com.example.users.domain.model.entity.uservalidates;

import com.example.users.domain.model.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserEmail {
    private static final String MESSAGE_MANDATORY = "Email is mandatory";
    private static final String MESSAGE_EMAIL_SYMBOL = "The email must contain exactly one '@' symbol.";
    private static final String MESSAGE_EMAIL_INVALID_POINT = "Email Invalid Verify Point";
    private static final String MESSAGE_EMAIL_INVALID = "Email Invalid";

    String email;
    public static UserEmail of  (String email){
        toValidEmail(email);
        return new UserEmail(email);
    }
    private static void toValidEmail(String email){
        if (email == null || email.isEmpty()) {
            throw new UserException(MESSAGE_MANDATORY);
        }

        long atSymbolCount = email.chars().filter(c -> c == '@').count();

        if (atSymbolCount != 1) {
            throw new UserException(MESSAGE_EMAIL_SYMBOL);
        }

        String[] parts = email.split("@");
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
    }
}
