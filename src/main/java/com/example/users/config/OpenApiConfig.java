package com.example.users.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Microservice Stock",
                description = "This is Api Microservice Stock First Module"
        )
)
public class OpenApiConfig {
}
