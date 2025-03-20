package com.revive.marketplace.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserProfileController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Check if user is authenticated and not anonymous
        if (authentication == null || !authentication.isAuthenticated() ||
              "anonymousUser".equals(authentication.getPrincipal())) {
            return ResponseEntity.status(401).body("User not authenticated");
        }
        
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        UserDTO userDTO = new UserDTO(
              user.getId(),
              user.getUsername(),
              user.getEmail(),
              user.getPhonenumber(),
              user.getAddress(),
              user.getRole().toString()
        );
        
        return ResponseEntity.ok(userDTO);
    }
}
