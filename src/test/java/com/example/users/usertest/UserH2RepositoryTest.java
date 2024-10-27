package com.example.users.usertest;

import com.example.users.domain.model.entity.Role;
import com.example.users.domain.model.entity.User;
import com.example.users.infrastructure.adapter.entity.UserEntity;
import com.example.users.infrastructure.adapter.jpa.UserSpringJpaAdapterRepository;
import com.example.users.infrastructure.adapter.jpa.respository.UserH2Repository;
import com.example.users.infrastructure.adapter.mapper.UserDboMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserH2RepositoryTest {

    @Mock
    private UserSpringJpaAdapterRepository userSpringJpaAdapterRepository;

    @Mock
    private UserDboMapper userDboMapper;

    @InjectMocks
    private UserH2Repository userH2Repository;

    private User testUser;
    private UserEntity testUserEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser =  new User (1L,"nombre","apellido","12365487",
                "+573177722509",LocalDate.of(2001, 9, 24),
                "test@example.com",
               "esteban123@", Role.ADMIN ,0,false, new Timestamp(System.currentTimeMillis()));
        testUserEntity = new UserEntity(1L,"nombre","apellido",
                "12365487","+573177722509",LocalDate.of(2001, 9, 24),
                "test@example.com",
                "esteban123@", Role.ADMIN ,0,false, new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void testCreate_ShouldReturnCreatedUser() {
        // Simulamos el mapeo de User a UserEntity
        when(userDboMapper.toDatabase(any(User.class))).thenReturn(testUserEntity);
        when(userSpringJpaAdapterRepository.save(any(UserEntity.class))).thenReturn(testUserEntity);
        when(userDboMapper.toDomain(any(UserEntity.class))).thenReturn(testUser);

        // Ejecutamos el método create
        User createdUser = userH2Repository.create(testUser);

        // Verificamos que se llame correctamente y devolvemos el usuario esperado
        assertNotNull(createdUser);
        assertEquals(testUser, createdUser);

        // Verificamos que el método save del repositorio fue invocado
        verify(userSpringJpaAdapterRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testDeleteById_ShouldInvokeDeleteMethod() {
        Long userId = 1L;

        // Ejecutamos el método deleteById
        userH2Repository.deleteById(userId);

        // Verificamos que el método deleteById fue invocado correctamente
        verify(userSpringJpaAdapterRepository, times(1)).deleteById(userId);
    }

    @Test
    void testUpdate_ShouldReturnUpdatedUser() {
        // Simulamos el mapeo de User a UserEntity
        when(userDboMapper.toDatabase(any(User.class))).thenReturn(testUserEntity);
        when(userSpringJpaAdapterRepository.save(any(UserEntity.class))).thenReturn(testUserEntity);
        when(userDboMapper.toDomain(any(UserEntity.class))).thenReturn(testUser);

        // Ejecutamos el método update
        User updatedUser = userH2Repository.update(testUser);

        // Verificamos que se haya actualizado correctamente
        assertNotNull(updatedUser);
        assertEquals(testUser, updatedUser);

        // Verificamos que el método save del repositorio fue invocado para la actualización
        verify(userSpringJpaAdapterRepository, times(1)).save(any(UserEntity.class));
    }
}
