package com.example.users.infrastructure.rest.controller;

import com.example.users.application.command.UserAuxCreateHandler;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name ="Article Command Controller")
public class UserCommandController {
    private final UserAuxCreateHandler userAuxCreateHandler;

    @Operation(summary = "Create User Aux Bodega")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created", content = @Content),
            @ApiResponse(responseCode = "409", content = @Content)
    })
    @PostMapping("/register/aux_bodega")
    public UserDto createAux (@RequestBody UserCreateCommand createCommand){
        return userAuxCreateHandler.execute(createCommand);
    }
}
