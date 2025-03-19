package com.revive.marketplace.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserController userController;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("JohnDoe");
        mockUser.setPassword("hashedpassword");
        mockUser.setEmail("johndoe@example.com");
        mockUser.setPhonenumber("+1234567890");
        mockUser.setAddress("123 Main Street, City");
        mockUser.setRole(User.Role.USER);
    }

    @Test
    @DisplayName("Should register a new user successfully")
    void should_Register_User_Successfully() {
        ResponseEntity<UserDTO> response = userController.registerUser(mockUser);

        assertNotNull(response.getBody(), "Response body should not be null");
        UserDTO responseBody = response.getBody();

        if (responseBody != null) {
            assertEquals(mockUser.getUsername(), responseBody.getUsername());
        }
    }

    @Test
    @DisplayName("Should login user successfully")
    void should_Login_User_Successfully() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail(mockUser.getEmail());
        loginRequest.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userService.getUserByEmail(mockUser.getEmail())).thenReturn(mockUser);

        ResponseEntity<?> response = userController.loginUser(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof UserDTO);
    }

    @Test
    @DisplayName("Should fail login with incorrect credentials")
    void should_Fail_Login_With_Incorrect_Credentials() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("wrong@example.com");
        loginRequest.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        ResponseEntity<?> response = userController.loginUser(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    @DisplayName("Should return user details by ID")
    void should_Get_User_By_ID() {
        when(userService.getUserById(mockUser.getId())).thenReturn(mockUser);

        ResponseEntity<UserDTO> response = userController.getUser(mockUser.getId());

        // Verificar que la respuesta no es nula
        assertNotNull(response, "ResponseEntity should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar que el cuerpo no es nulo antes de acceder a sus métodos
        UserDTO responseBody = response.getBody();
        assertNotNull(responseBody, "Response body should not be null");

        assertEquals(mockUser.getUsername(), responseBody.getUsername());
    }
/*  @Test
    @DisplayName("Should update user details successfully")
    void should_Update_User_Successfully() {
        User updatedUser = new User();
        updatedUser.setUsername("JaneDoe");
        updatedUser.setEmail("janedoe@example.com");
        updatedUser.setPhonenumber("+9876543210");
        updatedUser.setAddress("456 Another Street, City");
        updatedUser.setPassword("newpassword");
    
        when(userService.getUserById(mockUser.getId())).thenReturn(mockUser);
        when(passwordEncoder.encode(anyString())).thenReturn("hashednewpassword");
        when(userService.saveUser(any(User.class))).thenReturn(mockUser);
    
        ResponseEntity<UserDTO> response = userController.updateUser(mockUser.getId(), updatedUser);
    
        assertNotNull(response, "ResponseEntity should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    
        // Asegurar que el cuerpo no sea nulo antes de acceder a sus métodos
        UserDTO responseBody = response.getBody();
        assertNotNull(responseBody, "Response body should not be null");
    
        assertEquals("JaneDoe", responseBody.getUsername());
        assertEquals("janedoe@example.com", responseBody.getEmail());
    }*/
   
  

    @Test
    @DisplayName("Should delete a user successfully")
    void should_Delete_User_Successfully() {
        doNothing().when(userService).deleteUserById(mockUser.getId());

        ResponseEntity<String> response = userController.deleteUser(mockUser.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
    }

    @Test
    @DisplayName("Should list all users successfully")
    void should_List_All_Users_Successfully() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setUsername("JaneDoe");
        anotherUser.setEmail("janedoe@example.com");
        anotherUser.setPhonenumber("+9876543210");
        anotherUser.setAddress("456 Another Street, City");
        anotherUser.setRole(User.Role.ADMIN);

        when(userService.getAllUsers()).thenReturn(Arrays.asList(mockUser, anotherUser));

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<UserDTO> responseBody = response.getBody();
        assertNotNull(responseBody, "Response body should not be null");
        assertEquals(2, responseBody.size());
    }

    @Test
    @DisplayName("Should return 404 when user is not found")
    void should_Return_404_When_User_Not_Found() {
        when(userService.getUserById(99L)).thenThrow(new RuntimeException("User not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> userController.getUser(99L));

        assertEquals("User not found", exception.getMessage());
    }
}

