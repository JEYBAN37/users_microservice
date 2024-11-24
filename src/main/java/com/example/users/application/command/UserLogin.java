package com.example.users.application.command;
import com.example.users.domain.model.dto.AuthenticationRequest;
import com.example.users.domain.model.dto.AuthenticationResponse;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.exception.UserException;
import com.example.users.domain.port.JwtPort;
import com.example.users.domain.service.LoginAttemptService;
import com.example.users.infrastructure.adapter.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.example.users.domain.model.constant.UserConstant.*;


@AllArgsConstructor
public class UserLogin {

    private final AuthenticationManager authenticationManager;
    private final JwtPort jwtService;
    private final LoginAttemptService loginAttemptService;

    public AuthenticationResponse execute(AuthenticationRequest request) {
        try {

            if (loginAttemptService.isBlocked(request.getEmail())) {
                throw new UserException(USER_BLOCKED);
            }


            UsernamePasswordAuthenticationToken userCredential = new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            );

            Authentication authentication = authenticationManager.authenticate(userCredential);

            UserEntity user = (UserEntity) authentication.getPrincipal();

            loginAttemptService.loginSucceeded(request.getEmail());

            String jwtToken = jwtService.generate(new UserDto(
                    user.getId(),
                    user.getName(),
                    user.getLastName(),
                    user.getDni(),
                    user.getTelephone(),
                    user.getDateAge(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole(),
                    user.getFails(),
                    user.isLocked(),
                    user.getLockTime()
            ));

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
