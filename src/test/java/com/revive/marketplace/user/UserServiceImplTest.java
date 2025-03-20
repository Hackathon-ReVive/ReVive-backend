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

public class UserServiceImplTest {

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
    void should_Save_User_FromDTO() {
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User savedUser = userService.saveUser(mockUserDTO);

        assertNotNull(savedUser);
        assertEquals(mockUser.getEmail(), savedUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should save user")
    void should_Save_User() {
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        User savedUser = userService.saveUser(mockUser);

        assertNotNull(savedUser);
        assertEquals("JohnDoe", savedUser.getUsername());
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    @DisplayName("Should return user by email")
    void should_Get_User_By_Email() {
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(mockUser);

        User retrievedUser = userService.getUserByEmail(mockUser.getEmail());

        assertNotNull(retrievedUser);
        assertEquals(mockUser.getEmail(), retrievedUser.getEmail());
        verify(userRepository, times(1)).findByEmail(mockUser.getEmail());
    }

    @Test
    @DisplayName("Should return all users")
    void should_Return_All_Users() {
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
    void should_Check_If_User_Exists_By_Email() {
        when(userRepository.existsByEmail("johndoe@example.com")).thenReturn(true);

        assertTrue(userService.existsByEmail("johndoe@example.com"));
    }

    @Test
    @DisplayName("Should delete user by ID")
    void should_Delete_User_ById() {
        doNothing().when(userRepository).deleteById(mockUser.getId());

        userService.deleteUserById(mockUser.getId());

        verify(userRepository, times(1)).deleteById(mockUser.getId());
    }

    @Test
    @DisplayName("Should return user by findUserByEmail")
    void should_Find_User_By_Email() {
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(mockUser);

        User retrievedUser = userService.findUserByEmail(mockUser.getEmail());

        assertNotNull(retrievedUser);
        assertEquals(mockUser.getEmail(), retrievedUser.getEmail());
        verify(userRepository, times(1)).findByEmail(mockUser.getEmail());
    }

    @Test
    @DisplayName("Should return user by username")
    void should_Find_User_By_Username() {
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(mockUser);

        User retrievedUser = userService.findByUsername(mockUser.getUsername());

        assertNotNull(retrievedUser);
        assertEquals(mockUser.getUsername(), retrievedUser.getUsername());
        verify(userRepository, times(1)).findByUsername(mockUser.getUsername());
    }

    @Test
    @DisplayName("Should return user by ID")
    void should_Get_User_By_Id() {
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));

        User retrievedUser = userService.getUserById(mockUser.getId());

        assertNotNull(retrievedUser);
        assertEquals(mockUser.getId(), retrievedUser.getId());
        verify(userRepository, times(1)).findById(mockUser.getId());
    }

    @Test
@DisplayName("Should check if user exists by username")
void should_Check_If_User_Exists_By_Username() {
    when(userRepository.existsByUsername(mockUser.getUsername())).thenReturn(true);

    boolean exists = userService.existsByUsername(mockUser.getUsername());

    assertTrue(exists);
    verify(userRepository, times(1)).existsByUsername(mockUser.getUsername());
}


}
