package com.example.users.domain.model.entity.uservalidates;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserEmail {
    private static final String MESSAGE_MANDATORY = "Email is mandatory";
    private static final String MESSAGE_EMAIL_SYMBOL = "The email must contain exactly one '@' symbol.";

    private static final String MESSAGE_EMAIL_INVALID = "Email Invalid";

    String email;
    public static UserEmail of  (String email){
        toValidEmail(email);
        return new UserEmail(email);
    }
    private static void toValidEmail(String email){
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_MANDATORY);
        }

        long atSymbolCount = email.chars().filter(c -> c == '@').count();

        if (atSymbolCount != 1) {
            throw new IllegalArgumentException(MESSAGE_EMAIL_SYMBOL);
        }

        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];

        if (username.isEmpty() || domain.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_EMAIL_INVALID);
        }

        if (!domain.contains(".")) {
            throw new IllegalArgumentException(MESSAGE_EMAIL_INVALID);
        }

        if (domain.startsWith(".") || domain.endsWith(".")) {
            throw new IllegalArgumentException(MESSAGE_EMAIL_INVALID);
        }
    }
}
