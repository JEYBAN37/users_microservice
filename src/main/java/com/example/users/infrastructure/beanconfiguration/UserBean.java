package com.example.users.infrastructure.beanconfiguration;

import com.example.users.application.mapper.UserDtoMapper;
import com.example.users.application.query.UserLogin;
import com.example.users.domain.port.JwtPort;
import com.example.users.domain.port.dao.UserDao;
import com.example.users.domain.port.repository.UserRepository;
import com.example.users.domain.service.LoginAttemptService;
import com.example.users.domain.service.UserCreateService;
import com.example.users.infrastructure.adapter.jpa.UserSpringJpaAdapterRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@AllArgsConstructor
public class UserBean {
    private final UserSpringJpaAdapterRepository userSpringJpaAdapterRepository;
    @Bean
    public UserCreateService userCreateService (UserRepository userRepository, UserDao userDao){
        return new UserCreateService(userRepository,userDao);
    }

    @Bean
    public UserLogin userLogin(AuthenticationManager authenticationManager, UserDao userDao,
                               JwtPort jwtPort, UserDtoMapper userDtoMapper, LoginAttemptService loginAttemptService){
        return new UserLogin(authenticationManager,userDao,jwtPort,userDtoMapper, loginAttemptService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userSpringJpaAdapterRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public LoginAttemptService loginAttemptService (UserRepository userRepository, UserDao userDao){
        return new LoginAttemptService(userDao,userRepository);
    }





}

