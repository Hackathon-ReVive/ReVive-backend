package com.revive.marketplace.config;

import com.revive.marketplace.user.User;
import com.revive.marketplace.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatabaseSeederTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DatabaseSeeder databaseSeeder;

  
    @Test
    @DisplayName("Debe insertar usuarios iniciales cuando la base de datos está vacía")
    void should_Insert_Initial_Users_When_Database_Is_Empty() throws Exception {
        // Configurar el mock de userRepository.count() solo en este test
        when(userRepository.count()).thenReturn(0L);

        // Ejecutar la inicialización de la base de datos
        databaseSeeder.initDatabase(userRepository, passwordEncoder).run();

        // Verificar que se guardaron dos usuarios
        verify(userRepository, times(2)).save(any(User.class));
    }

    @Test
    @DisplayName("No debe insertar usuarios si la base de datos ya tiene registros")
    void should_Not_Insert_Users_If_Database_Is_Not_Empty() throws Exception {
        when(userRepository.count()).thenReturn(1L);

        databaseSeeder.initDatabase(userRepository, passwordEncoder).run();

        // Verificar que no se guardaron usuarios adicionales
        verify(userRepository, never()).save(any(User.class));
    }
}
