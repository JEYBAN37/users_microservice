package com.example.users.domain.port.dao;



import com.example.users.domain.model.entity.Role;
import com.example.users.domain.model.entity.User;

import java.util.List;

public interface UserDao {
    User getByName(String name);
    User getById(Long id);
    boolean nameExist(String name);
    boolean idExist(Long id);
    List<User> getAll(int page, int size, boolean ascending, String byName, String byBrand, String byCategory);

    Role getByRole(Role role);
}
