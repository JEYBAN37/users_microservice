package com.example.users.application.query;
import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.domain.model.dto.AuthenticationRequest;
import com.example.users.domain.model.dto.AuthenticationResponse;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.port.JwtPort;
import com.example.users.domain.port.dao.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@AllArgsConstructor
public class UserLogin {
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtPort jwtService;
    private final UserDtoMapper userDtoMapper;

        public AuthenticationResponse execute (AuthenticationRequest request){
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
            ));

            UserDto user = userDtoMapper.toDto(userDao.getUser(request.getEmail()));
            String jwtToken = jwtService.generate(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();

        }
}
