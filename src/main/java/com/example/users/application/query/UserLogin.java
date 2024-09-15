package com.example.users.application.query;

import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.domain.model.dto.AuthenticationRequest;
import com.example.users.domain.model.dto.AuthenticationResponse;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.exception.UserException;
import com.example.users.domain.port.JwtPort;
import com.example.users.domain.port.dao.UserDao;
import com.example.users.domain.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@AllArgsConstructor
public class UserLogin {

    private static final String USER_BLOCKED = "The account is temporarily blocked. Please try again later.";
    private static final String INCORRECT_CREDENTIALS = "Incorrect email or password.";

    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtPort jwtService;
    private final UserDtoMapper userDtoMapper;
    private final LoginAttemptService loginAttemptService;

    public AuthenticationResponse execute(AuthenticationRequest request) {
        try {

            if (loginAttemptService.isBlocked(request.getEmail())) {
                throw new UserException(USER_BLOCKED);
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );


            loginAttemptService.loginSucceeded(request.getEmail());

            UserDto user = userDtoMapper.toDto(userDao.getUser(request.getEmail()));
            String jwtToken = jwtService.generate(user);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();

        } catch (BadCredentialsException e) {

            loginAttemptService.loginFailed(request.getEmail());

            if (loginAttemptService.isBlocked(request.getEmail())) {
                throw new UserException(USER_BLOCKED);
            }

            throw new UserException(INCORRECT_CREDENTIALS);
        }
    }
}
