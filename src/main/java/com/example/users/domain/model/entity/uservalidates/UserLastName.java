package com.example.users.domain.model.entity.uservalidates;

import com.example.users.domain.model.exception.UserException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.users.domain.model.constant.UserConstant.*;


@NoArgsConstructor
@Getter
public class UserLastName {


    String lastName;

    private UserLastName(String lastname) {
        this.lastName = lastname;
    }

    public static UserLastName of(String lastname) {
        return new UserLastName( toValidLastName(lastname));
    }

    private static String toValidLastName(String lastname){
        if(lastname == null || lastname.isEmpty())
            throw new UserException(MESSAGE_MANDATORY_LASTNAME);
        String lastnameTrip = lastname.trim().toUpperCase();
        if(lastnameTrip.length() > MAXIMUM_ALLOW_LETTERS)
            throw new UserException(MESSAGE_MAX_BIGGER_LASTNAME);
        return lastnameTrip;
    }
}
