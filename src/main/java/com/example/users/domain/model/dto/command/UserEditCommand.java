package com.example.users.domain.model.dto.command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserEditCommand {
    private String name;
    private String lastname;
    private String dni;
    private String telephone;
    private LocalDate dateAge;
    private String email;
    private String password;
    private Long role;
}
