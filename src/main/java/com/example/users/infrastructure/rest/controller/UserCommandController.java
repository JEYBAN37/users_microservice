package com.example.users.infrastructure.rest.controller;

import com.example.users.application.command.UserAuxCreateHandler;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@Tag(name = "User Command Controller", description = "Controller for handling user creation operations.")
public class UserCommandController {

    private final UserAuxCreateHandler userAuxCreateHandler;

    @Operation(
            summary = "Create Auxiliary User for Bodega",
            description = "Create a new auxiliary user associated with the Bodega role by providing necessary details. Returns the created user's information."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Auxiliary User successfully created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            }),
            @ApiResponse(responseCode = "409", description = "User already exists or there is a conflict", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })

    @PostMapping("/register/aux_bodega")
    public ResponseEntity<UserDto> createAux (@RequestBody UserCreateCommand createCommand) {
        return ResponseEntity.ok(userAuxCreateHandler.execute(createCommand));
    }
}
