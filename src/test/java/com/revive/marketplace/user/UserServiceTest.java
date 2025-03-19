package com.revive.marketplace.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("JohnDoe");
        mockUser.setEmail("johndoe@example.com");
        mockUser.setPhonenumber("+1234567890");
        mockUser.setAddress("123 Main Street, City");
        mockUser.setRole(User.Role.USER);
    }

    @Test
    @DisplayName("Should return user by ID")
    void should_Get_User_By_ID() {
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));

        User retrievedUser = userService.getUserById(mockUser.getId());

        assertNotNull(retrievedUser);
        assertEquals(mockUser.getUsername(), retrievedUser.getUsername());
        verify(userRepository, times(1)).findById(mockUser.getId());
    }

    @Test
    @DisplayName("Should throw exception when user ID not found")
    void should_Throw_Exception_When_User_Not_Found_By_ID() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getUserById(99L));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should return user by email")
    void should_Get_User_By_Email() {
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));

        User retrievedUser = userService.getUserByEmail(mockUser.getEmail());

        assertNotNull(retrievedUser);
        assertEquals(mockUser.getEmail(), retrievedUser.getEmail());
        verify(userRepository, times(1)).findByEmail(mockUser.getEmail());
    }

    @Test
    @DisplayName("Should throw exception when user email not found")
    void should_Throw_Exception_When_User_Not_Found_By_Email() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> userService.getUserByEmail("notfound@example.com"));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByEmail("notfound@example.com");
    }
    /*
     * @Test
     * 
     * @DisplayName("Should save user successfully")
     * void should_Save_User_Successfully() {
     * doNothing().when(userRepository).save(any(User.class));
     * 
     * userService.saveUser(mockUser);
     * 
     * verify(userRepository, times(1)).save(mockUser);
     * }
     */

    @Test
    @DisplayName("Should delete user by ID")
    void should_Delete_User_By_ID() {
        when(userRepository.existsById(mockUser.getId())).thenReturn(true);
        doNothing().when(userRepository).deleteById(mockUser.getId());

        userService.deleteUserById(mockUser.getId());

        verify(userRepository, times(1)).existsById(mockUser.getId());
        verify(userRepository, times(1)).deleteById(mockUser.getId());
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent user")
    void should_Throw_Exception_When_Deleting_Non_Existent_User() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.deleteUserById(99L));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).existsById(99L);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should return all users")
    void should_Return_All_Users() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setUsername("JaneDoe");
        anotherUser.setEmail("janedoe@example.com");
        anotherUser.setPhonenumber("+9876543210");
        anotherUser.setAddress("456 Another Street, City");
        anotherUser.setRole(User.Role.ADMIN);

        when(userRepository.findAll()).thenReturn(Arrays.asList(mockUser, anotherUser));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no users found")
    void should_Return_Empty_List_When_No_Users_Found() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertTrue(users.isEmpty());
        verify(userRepository, times(1)).findAll();
    }
}

