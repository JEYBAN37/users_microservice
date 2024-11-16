package com.example.users.domain.model.constant;
public class UserConstant {
    private UserConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String USER_NOT_FOUND = "Usuario no encontrado";
    public static final String USER_BLOCKED = "La cuenta está temporalmente bloqueada. Por favor, inténtelo más tarde.";
    public static final String INCORRECT_CREDENTIALS = "Correo electrónico o contraseña incorrectos.";
    public static final String MESSAGE_NOT_PERMITTED = "La persona es menor de edad. No está permitido.";
    public static final String MESSAGE_AGE_NULL = "La fecha de nacimiento no puede estar vacía.";
    public static final String MESSAGE_NOT_FUTURE = "La fecha de nacimiento no puede estar en el futuro.";
    public static final String MESSAGE_MANDATORY = "El DNI es obligatorio.";
    public static final String MESSAGE_MAX_BIGGER = "El DNI no debe tener más de 50 caracteres.";
    public static final String MESSAGE_NOT_DIGITS = "El DNI no debe contener caracteres alfanuméricos.";
    public static final String MESSAGE_MANDATORY_EMAIL = "El correo electrónico es obligatorio.";
    public static final String MESSAGE_EMAIL_SYMBOL = "El correo electrónico debe contener exactamente un símbolo '@'.";
    public static final String MESSAGE_EMAIL_INVALID_POINT = "El correo electrónico es inválido. Verifique el punto.";
    public static final String MESSAGE_EMAIL_INVALID = "El correo electrónico es inválido.";
    public static final String MESSAGE_MANDATORY_LASTNAME = "El apellido es obligatorio.";
    public static final String MESSAGE_MAX_BIGGER_LASTNAME = "El apellido no debe tener más de 50 caracteres.";
    public static final String MESSAGE_MANDATORY_NAME = "El nombre es obligatorio.";
    public static final String MESSAGE_MAX_BIGGER_NAME = "El nombre no debe tener más de 50 caracteres.";
    public static final String MESSAGE_MANDATORY_TELEPHONE = "El teléfono es obligatorio.";
    public static final String MESSAGE_MAX_BIGGER_TELEPHONE = "El teléfono no debe tener más de 13 caracteres.";
    public static final String MESSAGE_NOT_DIGITS_TELEPHONE = "El teléfono no debe contener caracteres alfanuméricos.";
    public static final String MESSAGE_MANDATORY_PASSWORD = "La contraseña es obligatoria.";
    public static final String MESSAGE_PASSWORD_TOO_SHORT = "La contraseña debe tener al menos 6 caracteres.";
    public static final String MESSAGE_PASSWORD_ALPHANUMERIC = "La contraseña debe contener al menos una letra y un número.";
    public static final String MESSAGE_PASSWORD_SPECIAL_CHARACTER = "La contraseña debe contener al menos un carácter especial.";
    public static final String ALREADY_AUTENTICATED = "El usuario ya está autenticado.";
    public static final String TOKEN_EXPIRED = "El token ha expirado.";
    public static final String MESSAGE_ERROR_ADD = "El usuario ya existe.";


    public static final String PASSWORD_ALPHANUMERIC_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d).+$";
    public static final String SPECIAL_CHARACTER_PATTERN = ".*[^a-zA-Z0-9].*";

    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";
    public static final String DETAILS = "details";
    public static final String AUTORIZATION = "Authorization";

    public static final String BEARER = "Bearer ";
    public static final String USER = "USER ";
    public static final String ROLES = "roles";



    public static final int MUM_ALLOW_AGE = 18;
    public static final int MAXIMUM_ALLOW_LETTERS = 50;
    public static final int MAXIMUM_ALLOW_LETTERS_NAME = 50;
    public static final int MAXIMUM_ALLOW_LETTERS_TELEPHONE = 13;
    public static final int MAX_ATTEMPTS = 3;
    public static final int LOCK_TIME_DURATION = 1 * 60 * 1000;
}
