package com.revive.marketplace.config;

import com.revive.marketplace.user.User;
import com.revive.marketplace.user.UserRepository;
import com.revive.marketplace.user.User.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DatabaseSeederIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DatabaseSeeder databaseSeeder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAllInBatch();
        userRepository.flush(); 
        try {
            databaseSeeder.initDatabase(userRepository, passwordEncoder).run();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred during database initialization: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Debe insertar usuarios iniciales cuando la base de datos está vacía")
    void should_Insert_Initial_Users_When_Database_Is_Empty() throws Exception {

      

        assertEquals(2, userRepository.count(), "Deben existir exactamente 2 usuarios en la base de datos");

        User admin = userRepository.findByEmail("admin@example.com").orElse(null);
        User user = userRepository.findByEmail("user@example.com").orElse(null);

        assertNotNull(admin, "El usuario administrador debe existir");
        assertNotNull(user, "El usuario regular debe existir");

        assertEquals(Role.ADMIN, admin.getRole(), "El rol del usuario admin debe ser ADMIN");
        assertEquals(Role.USER, user.getRole(), "El rol del usuario user debe ser USER");

        assertTrue(passwordEncoder.matches("admin123", admin.getPassword()), "La contraseña del admin debe ser válida");
        assertTrue(passwordEncoder.matches("user123", user.getPassword()), "La contraseña del user debe ser válida");
    }

    @Test
    @DisplayName("No debe insertar usuarios si la base de datos ya tiene registros")
    void should_Not_Insert_Users_If_Database_Is_Not_Empty() throws Exception {

        User existingUser = new User();
        existingUser.setUsername("existingUser");
        existingUser.setEmail("existing@example.com");
        existingUser.setPassword(passwordEncoder.encode("password"));
        existingUser.setPhonenumber("555-9999");
        existingUser.setAddress("Existing Street");
        existingUser.setRole(Role.USER);
        userRepository.save(existingUser);

        long userCountBefore = userRepository.count();

     

        assertEquals(userCountBefore, userRepository.count(),
                "No se deben insertar nuevos usuarios si la BD no está vacía");
    }
    @Test
    @DisplayName("Los usuarios insertados deben tener datos correctos")
    void should_Users_Have_Correct_Data() throws Exception {
      
    
        User admin = userRepository.findByEmail("admin@example.com").orElse(null);
        User user = userRepository.findByEmail("user@example.com").orElse(null);
    
        assertNotNull(admin, "El usuario administrador debe existir");
        assertNotNull(user, "El usuario regular debe existir");
    
        assertEquals("admin", admin.getUsername(), "El username del admin debe ser 'admin'");
        assertEquals("user", user.getUsername(), "El username del usuario debe ser 'user'");
    
        assertEquals("555-1234", admin.getPhonenumber(), "El teléfono del admin debe ser correcto");
        assertEquals("555-5678", user.getPhonenumber(), "El teléfono del usuario debe ser correcto");
    
        assertEquals(Role.ADMIN, admin.getRole(), "El rol del admin debe ser ADMIN");
        assertEquals(Role.USER, user.getRole(), "El rol del usuario debe ser USER");
    
        assertTrue(passwordEncoder.matches("admin123", admin.getPassword()), "La contraseña del admin debe estar encriptada correctamente");
        assertTrue(passwordEncoder.matches("user123", user.getPassword()), "La contraseña del usuario debe estar encriptada correctamente");
    }




    

    
}
