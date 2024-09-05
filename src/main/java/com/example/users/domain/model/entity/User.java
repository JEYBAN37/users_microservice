package com.example.users.domain.model.entity;

import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.model.entity.uservalidates.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private UserName name;
    private UserLastName lastname;
    private UserDni dni;
    private UserTelephone telephone;
    private UserDateAge dateAge;
    private UserEmail email;
    private String password;
    private Role role;

    public User requestToCreate(UserCreateCommand userCreateCommand)
    {
        this.name = UserName.of(userCreateCommand.getName());
        this.lastname = UserLastName.of(userCreateCommand.getLastname());
        this.dni = UserDni.of(userCreateCommand.getDni());
        this.telephone = UserTelephone.of(userCreateCommand.getTelephone());
        this.dateAge = UserDateAge.of(userCreateCommand.getDateAge());
        this.email = UserEmail.of(userCreateCommand.getEmail());
        this.password = userCreateCommand.getPassword();
        this.role = userCreateCommand.getRole();
        return this;
    }

    public String getName() {
        return name.getName();
    }
    public String getLastName() {
        return lastname.getLastName();
    }
    public String getDni() {return dni.getDni();
    }
    public String getTelephone() {
        return telephone.getTelephone();
    }

    public LocalDate getDateAge (){return dateAge.getDateAge();}

    public String getEmail () {return email.getEmail();}


}