package com.example.users.infrastructure.adapter.mapper;

import com.example.users.domain.model.dto.UserDto;
import com.example.users.domain.model.entity.User;
import com.example.users.infrastructure.adapter.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class UserDboMapper {
private final PasswordEncoder encoderPassword;
public UserEntity toDatabase (User domain){
    if(domain == null){
        return null;
    }
    return new UserEntity(
            domain.getId(),
            domain.getName(),
            domain.getLastName(),
            domain.getDni(),
            domain.getTelephone(),
            domain.getDateAge(),
            domain.getEmail(),
            encoderPassword.encode(domain.getPassword()),
            domain.getRole()
    );
}

    public User toDomain(UserEntity entity){
        if(entity == null){
            return null;
        }
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getDni(),
                entity.getTelephone(),
                entity.getDateAge(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole()
                );
    }

    public UserEntity toDto (UserDto userDto) {
        if(userDto == null){
            return null;
        }
        return new UserEntity(
                userDto.getId(),
                userDto.getName(),
                userDto.getLastName(),
                userDto.getDni(),
                userDto.getTelephone(),
                userDto.getDateAge(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole()
        );
    }

    public UserDto  toDto (UserEntity userEntity) {
        if(userEntity == null){
            return null;
        }
        return new UserDto(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getLastName(),
                userEntity.getDni(),
                userEntity.getTelephone(),
                userEntity.getDateAge(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRole()
        );
    }


}
