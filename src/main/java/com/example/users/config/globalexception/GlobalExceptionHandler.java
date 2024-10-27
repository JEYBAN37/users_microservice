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

import static com.example.users.domain.model.constant.UserConstant.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> buildResponseBody(String message, Object details) {
        Map<String, Object> body = new HashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE, message);
        body.put(DETAILS, details);
        return body;
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> UserHandleBrandException(UserException ex, WebRequest request) {
        Map<String, Object> body = buildResponseBody(ex.getErrorMessage(), request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<Object> handleLockedException(LockedException ex,WebRequest request) {
        Map<String, Object> body = buildResponseBody(ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.LOCKED);

    }

}
