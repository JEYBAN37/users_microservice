package com.example.users.domain.port;

import com.example.users.domain.model.dto.UserDto;


public interface JwtPort {
    String generateToken(UserDto userDto);
    boolean isTokenValid(String token, UserDto userDto);
    String extractUsername(String token);
    String generate(UserDto userDto);
}
