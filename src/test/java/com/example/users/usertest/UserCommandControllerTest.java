package com.example.users.usertest;

import com.example.users.application.command.UserAuxCreateHandler;
import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.model.exception.UserException;
import com.example.users.infrastructure.rest.controller.UserCommandController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserCommandControllerTest {

    @InjectMocks
    private UserCommandController userCommandController;

    @Mock
    private UserAuxCreateHandler userAuxCreateHandler;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userCommandController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_createAux_whenUserIsCreatedSuccessfully_shouldReturn200() throws Exception {
        // Arrange
        UserCreateCommand createCommand = new UserCreateCommand();
        // Set required fields on createCommand
        createCommand.setName("John");
        createCommand.setLastName("Doe");
        createCommand.setEmail("john.doe@example.com");
        createCommand.setPassword("securePassword123");

        UserDto userDto = new UserDto();
        userDto.setEmail("john.doe@example.com"); // Set appropriate fields on userDto

        // Mock the behavior of userAuxCreateHandler
        when(userAuxCreateHandler.execute(any(UserCreateCommand.class))).thenReturn(userDto);

        // Act & Assert
        mockMvc.perform(post("/admin/register/aux_bodega")
                        .contentType(MediaType.APPLICATION_JSON) // Ensure content type is set
                        .content(objectMapper.writeValueAsString(createCommand)))
                .andExpect(status().isOk());
    }

}
