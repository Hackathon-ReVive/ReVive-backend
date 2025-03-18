package com.revive.marketplace.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // Endpoint para obtener el usuario por ID
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole().toString());
    }
    
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
}

