package com.example.users.domain.model.entity.uservalidates;

import com.example.users.domain.model.exception.UserException;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;


@Getter
public class UserDateAge {
    private static final int MUM_ALLOW_AGE = 18;
    private static final String MESSAGE_NOT_PERMITTED = "The person is a minor. Not permitted.";
    private static final String MESSAGE_AGE_NULL = "The date of birth cannot be null.";
    private static final String MESSAGE_NOT_FUTURE = "The date of birth cannot be in the future.";
    LocalDate dateAge;

    private UserDateAge(LocalDate dateAge){
        this.dateAge = dateAge;
    }
    public static UserDateAge of (LocalDate dateAge){
        toValidDate(dateAge);
        return new UserDateAge(dateAge);
    }

    private static void toValidDate(LocalDate dateAge){
        if (dateAge == null) {
            throw new UserException(MESSAGE_AGE_NULL);
        }

        LocalDate today = LocalDate.now();

        if (dateAge.isAfter(today)) {
            throw new UserException(MESSAGE_NOT_FUTURE);
        }

        int age = Period.between(dateAge, today).getYears();

        if (age < MUM_ALLOW_AGE) {
            throw new UserException(MESSAGE_NOT_PERMITTED);
        }
    }
}
