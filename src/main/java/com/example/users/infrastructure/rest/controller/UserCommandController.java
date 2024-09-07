package com.example.users.infrastructure.rest.controller;

import com.example.users.application.command.UserAuxCreateHandler;
import com.example.users.application.command.UserDeleteHandler;
import com.example.users.application.command.UserUpdateHandler;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.model.dto.command.UserEditCommand;
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
    private final UserDeleteHandler userDeleteHandler;
    private  final UserUpdateHandler userUpdateHandler;
    private final UserAuxCreateHandler userAuxCreateHandler;

    @Operation(summary = "Update an existing User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/{id}")
    public UserDto update(@RequestBody UserEditCommand editCommand, @PathVariable Long id){
        return userUpdateHandler.execute(editCommand, id);
    }

    @Operation(summary = "Delete a User by their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        userDeleteHandler.execute(id);
    }

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
