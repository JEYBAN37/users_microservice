package com.example.users.domain.port.dao;



import com.example.users.domain.model.entity.Role;
import com.example.users.domain.model.entity.User;


public interface UserDao {
    User getUser (String email);
    boolean emailExist(String email);
    boolean idExist(Long id);
    Role getByRole(Role role);
}
