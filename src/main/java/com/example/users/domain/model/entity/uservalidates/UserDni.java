package com.example.users.domain.model.entity.uservalidates;

import com.example.users.domain.model.exception.UserException;

public class UserDni {
    private static final int MAXIMUM_ALLOW_LETTERS = 50;
    private static final String MESSAGE_MANDATORY = "Dni is mandatory";
    private static final String MESSAGE_MAX_BIGGER = "Dni don't be bigger than 50 characters";
    private static final String MESSAGE_NOT_DIGITS = "Dni don't Digits";

    String  dni;
    private UserDni(String dni) {
        this.dni = dni;
    }

    public static UserDni of  (String dni){
        toValidDni(dni);
        return new UserDni(dni);
    }

    private static void toValidDni(String dni){
        if(dni.isEmpty())
            throw new UserException(MESSAGE_MANDATORY);
        if(dni.length() > MAXIMUM_ALLOW_LETTERS)
            throw new UserException(MESSAGE_MAX_BIGGER);
        if (!dni.chars().allMatch(Character::isDigit))
            throw new UserException(MESSAGE_NOT_DIGITS);
    }

}
