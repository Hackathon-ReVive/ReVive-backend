package com.revive.marketplace.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;
    private UserDTO mockUserDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User(1L, "JohnDoe", "password123", "johndoe@example.com", "+1234567890", "123 Main Street, City",
                User.UserRole.USER);
        mockUserDTO = new UserDTO(1L, "JohnDoe", "johndoe@example.com", "+1234567890", "123 Main Street, City", "USER");

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword123");
    }

    @Test
    @DisplayName("Should save user from DTO")
    void shouldSaveUserFromDTO() {
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User savedUser = userService.saveUser(mockUserDTO);

        assertNotNull(savedUser);
        assertEquals(mockUser.getEmail(), savedUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should save user")
    void shouldSaveUser() {
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        User savedUser = userService.saveUser(mockUser);

        assertNotNull(savedUser);
        assertEquals("JohnDoe", savedUser.getUsername());
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    @DisplayName("Should return user by email")
    void shouldGetUserByEmail() {
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(mockUser);

        User retrievedUser = userService.getUserByEmail(mockUser.getEmail());

        assertNotNull(retrievedUser);
        assertEquals(mockUser.getEmail(), retrievedUser.getEmail());
        verify(userRepository, times(1)).findByEmail(mockUser.getEmail());
    }

    @Test
    @DisplayName("Should return all users")
    void shouldReturnAllUsers() {
        User anotherUser = new User(2L, "JaneDoe", "password456", "janedoe@example.com", "+9876543210",
                "456 Another Street, City", User.UserRole.ADMIN);
        when(userRepository.findAll()).thenReturn(Arrays.asList(mockUser, anotherUser));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should check if user exists by email")
    void shouldCheckIfUserExistsByEmail() {
        when(userRepository.existsByEmail("johndoe@example.com")).thenReturn(true);

        assertTrue(userService.existsByEmail("johndoe@example.com"));
    }

    @Test
    @DisplayName("Should delete user by ID")
    void shouldDeleteUserById() {
        doNothing().when(userRepository).deleteById(mockUser.getId());

        userService.deleteUserById(mockUser.getId());

        verify(userRepository, times(1)).deleteById(mockUser.getId());
    }

}