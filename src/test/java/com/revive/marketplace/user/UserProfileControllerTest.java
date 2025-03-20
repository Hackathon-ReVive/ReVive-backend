package com.revive.marketplace.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProfileControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserProfileController userProfileController;

    private User mockUser;
    private UserDTO mockUserDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);

        mockUser = new User(1L, "JohnDoe", "password123", "johndoe@example.com", "+1234567890", "123 Main Street, City", User.UserRole.USER);
        mockUserDTO = new UserDTO(1L, "JohnDoe", "johndoe@example.com", "+1234567890", "123 Main Street, City", "USER");
    }


    

    @Test
    @DisplayName("Should return 401 when user is not authenticated")
    void should_Return_Unauthorized_For_Unauthenticated_User() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        ResponseEntity<?> response = userProfileController.getCurrentUser();

        assertEquals(401, response.getStatusCode().value());
        assertEquals("User not authenticated", response.getBody());
    }

    @Test
    @DisplayName("Should return 401 when principal is anonymousUser")
    void should_Return_Unauthorized_For_Anonymous_User() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("anonymousUser");

        ResponseEntity<?> response = userProfileController.getCurrentUser();

        assertEquals(401, response.getStatusCode().value());
        assertEquals("User not authenticated", response.getBody());
    }

    @Test
    @DisplayName("Should throw exception when user is not found")
    void should_ThrowException_When_User_NotFound() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(mockUser.getEmail());
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userProfileController.getCurrentUser());
        assertEquals("User not found", exception.getMessage());
    }
}
