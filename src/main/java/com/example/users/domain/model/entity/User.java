package com.example.users.domain.model.entity;

import com.example.users.domain.model.dto.command.UserCreateCommand;
import com.example.users.domain.model.entity.uservalidates.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private UserName name;
    private UserLastName lastName;
    private UserDni dni;
    private UserTelephone telephone;
    private UserDateAge dateAge;
    private UserEmail email;
    private UserPassword password;
    private Role role;
    private int fails;
    private boolean locked;
    private Timestamp lockTime;

    public User(Long id, String name, String lastName, String dni, String telephone,
                LocalDate dateAge, String email, String password, Role role,int fails,boolean locked,Timestamp lockTime){
        this.id = id;
        this.name = UserName.of(name);
        this.lastName = UserLastName.of(lastName);
        this.dni = UserDni.of(dni);
        this.telephone = UserTelephone.of(telephone);
        this.dateAge = UserDateAge.of(dateAge);
        this.email  = UserEmail.of(email);
        this.password = UserPassword.of(password);
        this.role = role;
        this.fails = fails;
        this.locked = locked;
        this.lockTime = lockTime;
    }
    public User requestToCreate(UserCreateCommand userCreateCommand)
    {
        this.name = UserName.of(userCreateCommand.getName());
        this.lastName = UserLastName.of(userCreateCommand.getLastName());
        this.dni = UserDni.of(userCreateCommand.getDni());
        this.telephone = UserTelephone.of(userCreateCommand.getTelephone());
        this.dateAge = UserDateAge.of(userCreateCommand.getDateAge());
        this.email = UserEmail.of(userCreateCommand.getEmail());
        this.password = UserPassword.of(userCreateCommand.getPassword());
        this.role = userCreateCommand.getRole();
        this.fails = 0;
        this.locked = false;
        this.lockTime = null;
        return this;
    }

    public String getName() {
        return name.getName();
    }
    public String getLastName() {
        return lastName.getLastName();
    }
    public String getDni() {return dni.getDni();}
    public String getTelephone() {
        return telephone.getTelephone();
    }
    public LocalDate getDateAge (){return dateAge.getDateAge();}
    public String getEmail () {return email.getEmail();}
    public String getPassword () {return password.getPassword();}

}