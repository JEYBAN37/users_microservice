package com.example.users.domain.model.entity.uservalidates;
import com.example.users.domain.model.exception.UserException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static com.example.users.domain.model.constant.UserConstant.*;

@NoArgsConstructor
@Getter
public class UserName {
    String name;

    private UserName(String name) {
        this.name = name;
    }

    public static UserName of(String name) {
        return new UserName( toValidName(name));
    }

    private static String toValidName(String name){
        if(name == null || name.isEmpty())
            throw new UserException(MESSAGE_MANDATORY_NAME);
        String nameTrip = name.trim().toUpperCase();
        if(nameTrip.length() > MAXIMUM_ALLOW_LETTERS_NAME)
            throw new UserException(MESSAGE_MAX_BIGGER_NAME);
        return nameTrip;
    }
}
