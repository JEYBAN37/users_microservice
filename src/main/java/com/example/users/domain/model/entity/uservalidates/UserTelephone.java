package com.example.users.domain.model.entity.uservalidates;

import com.example.users.domain.model.exception.UserException;
import lombok.Getter;
import static com.example.users.domain.model.constant.UserConstant.*;


@Getter
public class UserTelephone {
    String telephone;


    private UserTelephone(String telephone) {
        this.telephone = telephone;
    }

    public static UserTelephone of  (String telephone){
        return new UserTelephone(toValidTelephone(telephone));
    }

    private static String toValidTelephone(String telephone){
        if(telephone == null || telephone.isEmpty())
            throw new UserException(MESSAGE_MANDATORY_TELEPHONE);
        String telephoneTrim = telephone.trim();
        if(telephoneTrim.length() > MAXIMUM_ALLOW_LETTERS_TELEPHONE)
            throw new UserException(MESSAGE_MAX_BIGGER_TELEPHONE);
        if(telephoneTrim.charAt(0) != '+')
            throw new UserException(MESSAGE_NOT_DIGITS_TELEPHONE);
        String number = telephoneTrim.substring(1,telephoneTrim.length()-1);
        if (!number.chars().allMatch(Character::isDigit))
            throw new UserException(MESSAGE_NOT_DIGITS_TELEPHONE);
        return telephoneTrim;
    }
}
