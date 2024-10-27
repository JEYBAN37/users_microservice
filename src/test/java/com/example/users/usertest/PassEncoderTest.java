package com.example.users.usertest;

import com.example.users.infrastructure.adapter.jpa.encoder.PassEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PassEncoderTest {

    @Mock
    private PasswordEncoder passwordEncoder; // Simulamos el PasswordEncoder

    @InjectMocks
    private PassEncoder passEncoder; // Probamos la clase PassEncoder

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEncode_ShouldReturnEncodedPassword() {
        // Arrange
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword123";
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        // Act
        String result = passEncoder.encode(rawPassword);

        //Assert
        assertNotNull(result);
        assertEquals(encodedPassword, result);
    }

    @Test
    void testMatches_ShouldReturnTrue_WhenPasswordMatches() {
        // Arrange
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword123";
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // Act
        boolean result = passEncoder.matches(rawPassword, encodedPassword);

        // assert
        assertTrue(result);
    }

    @Test
    void testMatches_ShouldReturnFalse_WhenPasswordDoesNotMatch() {
        // Arrange
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword123";
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // Act
        boolean result = passEncoder.matches(rawPassword, encodedPassword);

        // assert
        assertFalse(result);
    }
}
