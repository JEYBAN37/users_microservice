package com.example.users.infrastructure.adapter.jpa.respository;

import com.example.users.domain.model.entity.User;
import com.example.users.domain.port.repository.UserRepository;
import com.example.users.infrastructure.adapter.entity.UserEntity;
import com.example.users.infrastructure.adapter.jpa.UserSpringJpaAdapterRepository;
import com.example.users.infrastructure.adapter.mapper.UserDboMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@AllArgsConstructor
public class UserH2Repository implements UserRepository {
    private final UserSpringJpaAdapterRepository userSpringJpaAdapterRepository;
    private final UserDboMapper userDboMapper;

    @Override
    public User create(User request) {
        UserEntity userToSave = userDboMapper.toDatabase(request);
        UserEntity userSaved = userSpringJpaAdapterRepository.save(userToSave);
        return userDboMapper.toDomain(userSaved);
    }

    @Override
    public void deleteById(Long id) {
        userSpringJpaAdapterRepository.deleteById(id);
    }

    @Override
    public User update(User request) {
        UserEntity userToUpdate = userDboMapper.toDatabase(request);
        UserEntity userUpdate = userSpringJpaAdapterRepository.save(userToUpdate);
        return userDboMapper.toDomain(userUpdate);
    }
}
