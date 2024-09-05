package com.example.users.domain.model.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@NoArgsConstructor(force = true)
@Getter
@Setter
public class UserException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    private final String errorMessage;
    public UserException(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
