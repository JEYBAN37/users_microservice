package com.example.users.infrastructure.rest.controller;

import com.example.users.application.command.UserCreateHandler;
import com.example.users.application.query.UserLogin;
import com.example.users.domain.model.dto.AuthenticationRequest;
import com.example.users.domain.model.dto.AuthenticationResponse;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
@Tag(name ="User Auth Controller")
public class UserSecurityController {
    private final UserCreateHandler userCreateHandler;
    private final UserLogin userLogin;


    @Operation(summary = "Login User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article created", content = @Content),
            @ApiResponse(responseCode = "409", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login ( @RequestBody AuthenticationRequest request) {
     return ResponseEntity.ok(userLogin.execute(request));
    }

    @Operation(summary = "Register new User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article created", content = @Content),
            @ApiResponse(responseCode = "409", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserCreateCommand createCommand){
        return ResponseEntity.ok(userCreateHandler.execute(createCommand));
    }
}
