package com.example.users.domain.model.dto;

import com.example.users.domain.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String lastName;
    private String dni;
    private String telephone;
    private LocalDate dateAge;
    private String email;
    private String password;
    private Role role;
    private int fails;
    private boolean locked;
    private Timestamp lockTime;

}
