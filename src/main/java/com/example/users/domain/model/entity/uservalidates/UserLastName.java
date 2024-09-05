package com.example.users.domain.model.entity.uservalidates;

import com.example.users.domain.model.exception.UserException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserLastName {
    private static final int MAXIMUM_ALLOW_LETTERS = 50;
    private static final String MESSAGE_MANDATORY = "LastName is mandatory";
    private static final String MESSAGE_MAX_BIGGER = "LastName don't be bigger than 50 characters";

    String lastName;

    private UserLastName(String lastname) {
        this.lastName = lastname;
    }

    public static UserLastName of(String lastname) {
        toValidName(lastname);
        return new UserLastName(lastname);
    }

    private static void toValidName(String lastname){
        if(lastname.isEmpty())
            throw new UserException(MESSAGE_MANDATORY);
        if(lastname.length() > MAXIMUM_ALLOW_LETTERS)
            throw new UserException(MESSAGE_MAX_BIGGER);
    }
}
