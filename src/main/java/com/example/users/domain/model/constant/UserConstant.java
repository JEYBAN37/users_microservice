package com.example.users.domain.model.constant;
public class UserConstant {
    private UserConstant() {
        throw new IllegalStateException("Utility class");
    }
    public static final String TASK_NOT_FOUND_MESSAGE_ERROR = "No found User with id %s";
    public static final String IS_AUTENTICATED = "User have token";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_BLOCKED = "The account is temporarily blocked. Please try again later.";
    public static final String INCORRECT_CREDENTIALS = "Incorrect email or password.";
    public static final String MESSAGE_NOT_PERMITTED = "The person is a minor. Not permitted.";
    public static final String MESSAGE_AGE_NULL = "The date of birth cannot be null.";
    public static final String MESSAGE_NOT_FUTURE = "The date of birth cannot be in the future.";
    public static final String MESSAGE_MANDATORY = "Dni is mandatory";
    public static final String MESSAGE_MAX_BIGGER = "Dni don't be bigger than 50 characters";
    public static final String MESSAGE_NOT_DIGITS = "Dni don't Digits";
    public static final String MESSAGE_MANDATORY_EMAIL = "Email is mandatory";
    public static final String MESSAGE_EMAIL_SYMBOL = "The email must contain exactly one '@' symbol.";
    public static final String MESSAGE_EMAIL_INVALID_POINT = "Email Invalid Verify Point";
    public static final String MESSAGE_EMAIL_INVALID = "Email Invalid";
    public static final String MESSAGE_MANDATORY_LASTNAME = "LastName is mandatory";
    public static final String MESSAGE_MAX_BIGGER_LASTNAME = "LastName don't be bigger than 50 characters";
    public static final String MESSAGE_MANDATORY_NAME = "Name is mandatory";
    public static final String MESSAGE_MAX_BIGGER_NAME = "Name don't be bigger than 50 characters";
    public static final String MESSAGE_MANDATORY_TELEPHONE = "telephone is mandatory";
    public static final String MESSAGE_MAX_BIGGER_TELEPHONE = "telephone don't be bigger than 13 characters";
    public static final String MESSAGE_NOT_DIGITS_TELEPHONE = "telephone don't be alphanumeric";
    public static final String MESSAGE_MANDATORY_PASSWORD = "Password is required.";
    public static final String MESSAGE_PASSWORD_TOO_SHORT = "Password must be at least 6 characters long.";
    public static final String MESSAGE_PASSWORD_ALPHANUMERIC = "Password must contain at least one letter and one number.";
    public static final String MESSAGE_PASSWORD_SPECIAL_CHARACTER = "Password must contain at least one special character.";
    public static final String PASSWORD_ALPHANUMERIC_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d).+$";
    public static final String SPECIAL_CHARACTER_PATTERN = ".*[^a-zA-Z0-9].*";
    public static final String MESSAGE_ERROR_ADD = "User Exist";
    public static final String MESSAGE_ERROR_ROLE = "Role Not Found";
    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";
    public static final String DETAILS = "details";
    public static final String AUTORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String ROLE_ADMIN = "ROLE_ADMIN ";
    public static final String USER = "USER ";
    public static final String TOKEN_EXPIRED = "Token ha expirado ";
    public static final String ROLES = "roles";


    public static final int MUM_ALLOW_AGE = 18;
    public static final int MAXIMUM_ALLOW_LETTERS = 50;
    public static final int MAXIMUM_ALLOW_LETTERS_NAME = 50;
    public static final int MAXIMUM_ALLOW_LETTERS_TELEPHONE = 13;
    public static final int MAX_ATTEMPTS = 3;
    public static final int LOCK_TIME_DURATION = 1 * 60 * 1000;
}
