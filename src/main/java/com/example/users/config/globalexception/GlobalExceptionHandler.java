package com.example.users.config.globalexception;


import com.example.users.domain.model.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MESSAGE = "message";
    private static final String TIMESTAMP = "timestamp";
    private static final String DETAILS = "details";
    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> brandHandleBrandException(UserException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE, ex.getErrorMessage());
        body.put(DETAILS, request.getDescription(false));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<String> handleLockedException(LockedException ex) {
        return ResponseEntity.status(HttpStatus.LOCKED).body("Usuario bloqueado temporalmente debido a demasiados intentos fallidos");
    }

}
