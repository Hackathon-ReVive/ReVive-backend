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
import org.springframework.security.authentication.BadCredentialsException;

import com.revive.marketplace.login.LoginRequestDTO;
import com.revive.marketplace.security.JwtUtil;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

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
        mockUser.setRole(User.UserRole.USER);
    }

   /*  @Test
    @DisplayName("Should register a new user successfully")
    void should_Register_User_Successfully() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userService.saveUser(any(User.class))).thenReturn(mockUser);

        ResponseEntity<UserDTO> response = userController.registerUser(mockUser);

        assertNotNull(response.getBody(), "Response body should not be null");
        UserDTO responseBody = response.getBody();

        assertNotNull(responseBody, "Response body should not be null");
        assertEquals(mockUser.getUsername(), responseBody.getUsername());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }*/

    @Test
    @DisplayName("Should login user successfully")
    void should_Login_User_Successfully() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail(mockUser.getEmail());
        loginRequest.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(mockUser);
        when(jwtUtil.generateToken(mockUser)).thenReturn("mockJwtToken");

        ResponseEntity<?> response = userController.loginUser(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof java.util.Map);
    }

    @Test
    @DisplayName("Should fail login with incorrect credentials")
    void should_Fail_Login_With_Incorrect_Credentials() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("wrong@example.com");
        loginRequest.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new BadCredentialsException("Invalid credentials"));

        ResponseEntity<?> response = userController.loginUser(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    @DisplayName("Should return user details by ID")
    void should_Get_User_By_ID() {
        when(userService.getUserById(mockUser.getId())).thenReturn(mockUser);

        ResponseEntity<UserDTO> response = userController.getUser(mockUser.getId());

        assertNotNull(response, "ResponseEntity should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserDTO responseBody = response.getBody();
        assertNotNull(responseBody, "Response body should not be null");
        assertEquals(mockUser.getUsername(), responseBody.getUsername());
    }

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
        anotherUser.setRole(User.UserRole.ADMIN);

        when(userService.getAllUsers()).thenReturn(Arrays.asList(mockUser, anotherUser));

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<UserDTO> responseBody = response.getBody();
        assertNotNull(responseBody, "Response body should not be null");
        assertEquals(2, responseBody.size());
    }
}