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
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar el usuario por su nombre de usuario
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        // Si no se encuentra el usuario, lanzar una excepción
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        
        User user = userOptional.get();
        
        // Convertir el rol del usuario a una autoridad para Spring Security
        // Si el rol es USER, se crea una autoridad ROLE_USER, y si es ADMIN, ROLE_ADMIN
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
        
        // Devolver el objeto UserDetails con la información del usuario
        return new org.springframework.security.core.userdetails.User(
              user.getUsername(),
              user.getPassword(),
              // Como solo tienes un rol, no es necesario un stream, solo pasamos la autoridad
              java.util.Collections.singletonList(authority)
        );
    }
}
