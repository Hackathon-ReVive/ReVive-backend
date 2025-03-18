package com.revive.marketplace.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    // Endpoint para registrar un nuevo usuario
    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }
        
        userService.saveUser(user);
        
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole().toString());
    }
    
    // Endpoint para login de usuario
    @PostMapping("/login")
    public UserDTO loginUser(@RequestBody LoginRequestDTO loginRequest) {
        authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        
        User user = userService.getUserByEmail(loginRequest.getEmail());
        
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole().toString());
    }
    
    // Endpoint para obtener el usuario por ID
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole().toString());
    }
}
