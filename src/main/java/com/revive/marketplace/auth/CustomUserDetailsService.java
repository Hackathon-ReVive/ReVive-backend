package com.revive.marketplace.auth;

import com.revive.marketplace.user.UserRepository;
import com.revive.marketplace.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar al usuario por email en lugar de por username
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        // Si el usuario no existe, lanzar una excepción
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        }
        
        User user = userOptional.get();
        
        // Convertir el rol a una autoridad para Spring Security
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
        
        // Devolver el UserDetails, ahora usando email en lugar de username
        return new org.springframework.security.core.userdetails.User(
              user.getEmail(),  // Usar el email en lugar del username
              user.getPassword(),
              java.util.Collections.singletonList(authority)  // Roles del usuario
        );
    }
}
