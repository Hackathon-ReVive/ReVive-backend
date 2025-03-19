package com.revive.marketplace.config;

import com.revive.marketplace.user.User;
import com.revive.marketplace.user.UserRepository;
import com.revive.marketplace.user.User.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseSeeder {
    
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) { // Solo insertar si la BD está vacía
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setPhonenumber("555-1234");
                admin.setAddress("Admin Street 1");
                admin.setRole(Role.ADMIN);
                
                User user = new User();
                user.setUsername("user");
                user.setEmail("user@example.com");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setPhonenumber("555-5678");
                user.setAddress("User Avenue 2");
                user.setRole(Role.USER);
                
                userRepository.save(admin);
                userRepository.save(user);
                
                System.out.println("Usuarios iniciales insertados correctamente.");
            }
        };
    }
}
