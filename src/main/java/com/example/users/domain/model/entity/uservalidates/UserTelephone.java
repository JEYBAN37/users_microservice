package com.example.users.domain.model.entity.uservalidates;

import com.example.users.domain.model.exception.UserException;
import lombok.Getter;

@Getter
public class UserTelephone {
    String telephone;
    private static final int MAXIMUM_ALLOW_LETTERS = 13;
    private static final String MESSAGE_MANDATORY = "LastName is mandatory";
    private static final String MESSAGE_MAX_BIGGER = "LastName don't be bigger than 50 characters";
    private static final String MESSAGE_NOT_DIGITS = "LastName don't be bigger than 50 characters";
    private UserTelephone(String telephone) {
        this.telephone = telephone;
    }

    public static UserTelephone of  (String telephone){
        toValidTelephone(telephone);
        return new UserTelephone(telephone);
    }

    private static void toValidTelephone(String telephone){
        if(telephone.isEmpty())
            throw new UserException(MESSAGE_MANDATORY);
        if(telephone.length() > MAXIMUM_ALLOW_LETTERS)
            throw new UserException(MESSAGE_MAX_BIGGER);
        if(telephone.charAt(0) != '+')
            throw new UserException(MESSAGE_NOT_DIGITS);
        String number = telephone.substring(1,telephone.length()-1);
        if (!number.chars().allMatch(Character::isDigit))
            throw new UserException(MESSAGE_NOT_DIGITS);
    }

}
