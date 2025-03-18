package com.revive.marketplace.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    // Registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }
        
        userService.saveUser(user);
        
        UserDTO userDTO = new UserDTO(
              user.getId(), user.getUsername(), user.getEmail(),
              user.getPhonenumber(), user.getAddress(), user.getRole().toString()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
    
    
    // Login de usuario
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        try {
            authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            
            User user = userService.getUserByEmail(loginRequest.getEmail());
            
            UserDTO userDTO = new UserDTO(
                  user.getId(), user.getUsername(), user.getEmail(),
                  user.getPhonenumber(), user.getAddress(), user.getRole().toString()
            );
            
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
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
