package com.example.users.infrastructure.rest.controller;

import com.example.users.application.command.UserClientCreateHandler;
import com.example.users.application.command.UserCreateHandler;
import com.example.users.application.query.UserLogin;
import com.example.users.domain.model.dto.AuthenticationRequest;
import com.example.users.domain.model.dto.AuthenticationResponse;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "User Auth Controller", description = "Operations related to User Authentication")
public class UserSecurityController {

    private final UserCreateHandler userCreateHandler;
    private final UserClientCreateHandler userClientCreateHandler;
    private final UserLogin userLogin;

    @Operation(
            summary = "User Login",
            description = "Authenticate a user using username and password and return a JWT token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid credentials or missing data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userLogin.execute(request));
    }

    @Operation(
            summary = "Register new Admin User",
            description = "Register a new admin user by providing necessary details. Returns a JWT token upon successful registration."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin user successfully registered", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "User already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody UserCreateCommand createCommand) {
        return ResponseEntity.ok(userCreateHandler.execute(createCommand));
    }

    @Operation(
            summary = "Register new Client User",
            description = "Register a new client user. Returns a JWT token upon successful registration."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client user successfully registered", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "User already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping("/register/client")
    public ResponseEntity<AuthenticationResponse> registerClient(@RequestBody UserCreateCommand createCommand) {
        return ResponseEntity.ok(userClientCreateHandler.execute(createCommand));
    }
}
