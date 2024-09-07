package com.example.users.domain.port;

public interface Encoder {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
