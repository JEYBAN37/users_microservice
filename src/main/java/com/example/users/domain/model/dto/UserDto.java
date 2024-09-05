package com.example.users.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String lastname;
    private String dni;
    private String telephone;
    private LocalDate dateAge;
    private String email;
    private String password;
    private String idRole;

}
