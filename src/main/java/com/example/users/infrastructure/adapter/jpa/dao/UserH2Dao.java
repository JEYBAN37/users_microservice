package com.example.users.infrastructure.adapter.jpa.dao;


import com.example.users.domain.model.constant.UserConstant;
import com.example.users.domain.model.entity.Role;
import com.example.users.domain.model.entity.User;
import com.example.users.domain.model.exception.UserException;
import com.example.users.domain.port.dao.UserDao;
import com.example.users.infrastructure.adapter.entity.UserEntity;
import com.example.users.infrastructure.adapter.jpa.UserSpringJpaAdapterRepository;
import com.example.users.infrastructure.adapter.mapper.UserDboMapper;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserH2Dao implements UserDao {

    UserSpringJpaAdapterRepository userSpringJpaAdapterRepository;
    private final UserDboMapper userDboMapper;


  @Override
  public User getUser(String email) {
      Optional<UserEntity> optionalUser = userSpringJpaAdapterRepository.findByEmail(email);
      return optionalUser.map(userDboMapper::toDomain).orElse(null);
    }
    @Override
    public boolean emailExist(String email) {return userSpringJpaAdapterRepository.existsByEmail(email);
    }

    @Override
    public boolean idExist(Long id) {
        return userSpringJpaAdapterRepository.existsById(id);
    }

    @Override
    public Role getByRole(Role role) {
        return null;
    }


}
