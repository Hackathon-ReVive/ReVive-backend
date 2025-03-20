package com.revive.marketplace.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO(
                1L,
                "JohnDoe",
                "johndoe@example.com",
                "+1234567890",
                "123 Main Street, City",
                "USER");
    }

    @Test
    @DisplayName("Should correctly initialize UserDTO with parameters")
    void should_Create_UserDTO_With_Valid_Parameters() {
        assertNotNull(userDTO);
        assertEquals(1L, userDTO.getId());
        assertEquals("JohnDoe", userDTO.getUsername());
        assertEquals("johndoe@example.com", userDTO.getEmail());
        assertEquals("+1234567890", userDTO.getPhonenumber());
        assertEquals("123 Main Street, City", userDTO.getAddress());
        assertEquals("USER", userDTO.getRole());
    }

    @Test
    @DisplayName("Should correctly return values from getters")
    void should_Return_Correct_Values_From_Getters() {
        assertEquals(1L, userDTO.getId());
        assertEquals("JohnDoe", userDTO.getUsername());
        assertEquals("johndoe@example.com", userDTO.getEmail());
        assertEquals("+1234567890", userDTO.getPhonenumber());
        assertEquals("123 Main Street, City", userDTO.getAddress());
        assertEquals("USER", userDTO.getRole());
    }

    @Test
    @DisplayName("Should correctly update values using setters")
    void should_Update_Values_Using_Setters() {
        userDTO.setId(2L);
        userDTO.setUsername("JaneDoe");
        userDTO.setEmail("janedoe@example.com");
        userDTO.setPhonenumber("+9876543210");
        userDTO.setAddress("456 Another Street, City");
        userDTO.setRole("ADMIN");

        assertEquals(2L, userDTO.getId());
        assertEquals("JaneDoe", userDTO.getUsername());
        assertEquals("janedoe@example.com", userDTO.getEmail());
        assertEquals("+9876543210", userDTO.getPhonenumber());
        assertEquals("456 Another Street, City", userDTO.getAddress());
        assertEquals("ADMIN", userDTO.getRole());
    }

    @Test
    @DisplayName("Should allow setting null values in optional fields")
    void should_Allow_Null_Values_In_Optional_Fields() {
        userDTO.setPhonenumber(null);
        userDTO.setAddress(null);

        assertNull(userDTO.getPhonenumber(), "Phone number should be null");
        assertNull(userDTO.getAddress(), "Address should be null");
    }

    @Test
    @DisplayName("Should allow setting empty strings in fields")
    void should_Allow_Empty_Strings_In_Fields() {
        userDTO.setUsername("");
        userDTO.setEmail("");
        userDTO.setPhonenumber("");
        userDTO.setAddress("");
        userDTO.setRole("");

        assertEquals("", userDTO.getUsername());
        assertEquals("", userDTO.getEmail());
        assertEquals("", userDTO.getPhonenumber());
        assertEquals("", userDTO.getAddress());
        assertEquals("", userDTO.getRole());
    }
}
