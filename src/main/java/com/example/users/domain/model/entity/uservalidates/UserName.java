package com.example.users.domain.model.entity.uservalidates;

import com.example.users.domain.model.exception.UserException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserName {
    private static final int MAXIMUM_ALLOW_LETTERS = 50;
    private static final String MESSAGE_MANDATORY = "Name is mandatory";
    private static final String MESSAGE_MAX_BIGGER = "Name don't be bigger than 50 characters";

    String name;

    private UserName(String name) {
        this.name = name;
    }

    public static UserName of(String name) {
        toValidName(name);
        return new UserName(name);
    }

    private static void toValidName(String name){
        if(name.isEmpty())
            throw new UserException(MESSAGE_MANDATORY);
        if(name.length() > MAXIMUM_ALLOW_LETTERS)
            throw new UserException(MESSAGE_MAX_BIGGER);
    }
}
