package com.revive.marketplace.user;

import com.revive.marketplace.login.LoginRequestDTO;
import com.revive.marketplace.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Map<String, String> errors = new HashMap<>();
        
        // Validate required fields (removing username and phone validation)
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            errors.put("email", "Email is required");
        } else {
            User existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser != null) {
                errors.put("email", "Email is already in use");
            }
        }
        
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            errors.put("password", "Password is required");
        }
        
        if (user.getAddress() == null || user.getAddress().trim().isEmpty()) {
            errors.put("address", "Address is required");
        }
        
        // Return errors if validation fails
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        
        try {
            // Set default values for null fields
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                user.setUsername("User_" + System.currentTimeMillis()); // Generate default username
            }
            
            // Phone can be null now
            
            // Encode password if not already encoded
            if (!user.getPassword().startsWith("$2a$")) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            
            // Set default role if not specified
            if (user.getRole() == null) {
                user.setRole(User.UserRole.USER);
            }
            
            User savedUser = userService.saveUser(user);
            
            UserDTO userDTO = new UserDTO(
                  savedUser.getId(),
                  savedUser.getUsername(),
                  savedUser.getEmail(),
                  savedUser.getPhonenumber(),
                  savedUser.getAddress(),
                  savedUser.getRole().toString()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        } catch (Exception e) {
            errors.put("server", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
        }
    }
    
    
    // Login de usuario
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        try {
            authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            
            User user = userRepository.findByEmail(loginRequest.getEmail());
            if (user == null) {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
            
            String token = jwtUtil.generateToken(user);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", new UserDTO(
                  user.getId(),
                  user.getUsername(),
                  user.getEmail(),
                  user.getPhonenumber(),
                  user.getAddress(),
                  user.getRole().toString()
            ));
            
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    
    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        
        UserDTO userDTO = new UserDTO(
              user.getId(), user.getUsername(), user.getEmail(),
              user.getPhonenumber(), user.getAddress(), user.getRole().toString()
        );
        
        return ResponseEntity.ok(userDTO);
    }
    
    // Actualizar usuario por ID
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.getUserById(id);
        
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPhonenumber(updatedUser.getPhonenumber());
        user.setAddress(updatedUser.getAddress());
        
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        
        userService.saveUser(user);
        
        UserDTO userDTO = new UserDTO(
              user.getId(), user.getUsername(), user.getEmail(),
              user.getPhonenumber(), user.getAddress(), user.getRole().toString()
        );
        
        return ResponseEntity.ok(userDTO);
    }
    
    // Eliminar usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
    
    // Listar todos los usuarios (Solo Admins)
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        
        List<UserDTO> userDTOs = users.stream()
              .map(user -> new UserDTO(
                    user.getId(), user.getUsername(), user.getEmail(),
                    user.getPhonenumber(), user.getAddress(), user.getRole().toString()))
              .collect(Collectors.toList());
        
        return ResponseEntity.ok(userDTOs);
    }
}
