package com.example.users.application.command;


import com.example.users.domain.service.UserDeleteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDeleteHandler {
    private final UserDeleteService userDeleteService;
    public void execute(Long articleId) {
        userDeleteService.execute(articleId);
    }
}
