package com.revive.marketplace.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestDTOTest {

    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    void setUp() {
        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("testuser@example.com");
        loginRequestDTO.setPassword("SecurePassword123");
    }

    @Test
    @DisplayName("Should correctly return values from getters")
    void should_Return_Correct_Values_From_Getters() {
        assertEquals("testuser@example.com", loginRequestDTO.getEmail());
        assertEquals("SecurePassword123", loginRequestDTO.getPassword());
    }

    @Test
    @DisplayName("Should correctly update values using setters")
    void should_Update_Values_Using_Setters() {
        loginRequestDTO.setEmail("newuser@example.com");
        loginRequestDTO.setPassword("NewSecurePass456");

        assertEquals("newuser@example.com", loginRequestDTO.getEmail());
        assertEquals("NewSecurePass456", loginRequestDTO.getPassword());
    }

    @Test
    @DisplayName("Should allow setting null values")
    void should_Allow_Null_Values() {
        loginRequestDTO.setEmail(null);
        loginRequestDTO.setPassword(null);

        assertNull(loginRequestDTO.getEmail(), "Email should be null");
        assertNull(loginRequestDTO.getPassword(), "Password should be null");
    }

    @Test
    @DisplayName("Should allow setting empty strings")
    void should_Allow_Empty_Strings() {
        loginRequestDTO.setEmail("");
        loginRequestDTO.setPassword("");

        assertEquals("", loginRequestDTO.getEmail());
        assertEquals("", loginRequestDTO.getPassword());
    }
}


