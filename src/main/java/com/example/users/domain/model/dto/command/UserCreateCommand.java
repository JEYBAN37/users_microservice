package com.example.users.domain.model.dto.command;

import com.example.users.domain.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserCreateCommand {
    private Long id;
    private String name;
    private String lastname;
    private String dni;
    private String telephone;
    private LocalDate dateAge;
    private String email;
    private String password;
    private Role role;
}
