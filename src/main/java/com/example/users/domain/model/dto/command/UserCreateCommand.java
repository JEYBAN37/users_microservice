package com.example.users.domain.model.dto.command;

import com.example.users.domain.model.entity.Role;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@Getter
public class UserCreateCommand {
    private Long id;
    private String name;
    private String lastName;
    private String dni;
    private String telephone;
    private LocalDate dateAge;
    private String email;
    private String password;
    private Role role;
}
