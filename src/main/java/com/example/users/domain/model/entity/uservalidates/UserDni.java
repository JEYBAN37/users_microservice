package com.example.users.domain.model.entity.uservalidates;

import com.example.users.domain.model.exception.UserException;
import lombok.Getter;

import static com.example.users.domain.model.constant.UserConstant.*;


@Getter
public class UserDni {

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
