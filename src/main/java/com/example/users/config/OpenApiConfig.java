package com.example.users.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Microservice User",
                description = "This is Api Microservice User Second Module"
        )
)
public class OpenApiConfig {
}
