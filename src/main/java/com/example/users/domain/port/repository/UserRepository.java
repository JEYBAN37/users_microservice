package com.example.users.domain.port.repository;


import com.example.users.domain.model.entity.User;

public interface UserRepository {
    User create (User request);
    void deleteById (Long id);
    User update (User request);
}
