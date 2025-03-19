package com.revive.marketplace.auth;

import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



class WebSecurityConfigTest {

    @InjectMocks
    private WebSecurityConfig webSecurityConfig;

    @Mock
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debería crear un UserDetailsService correctamente")
    void should_Create_UserDetailsService() {
        UserDetailsService userDetailsService = webSecurityConfig.userDetailsService();
        assertNotNull(userDetailsService, "El UserDetailsService no debería ser nulo");
    }

    @Test
    @DisplayName("Debería crear un PasswordEncoder correctamente")
    void should_Create_PasswordEncoder() {
        PasswordEncoder passwordEncoder = webSecurityConfig.passwordEncoder();
        assertNotNull(passwordEncoder, "El PasswordEncoder no debería ser nulo");
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder, "El PasswordEncoder debería ser una instancia de BCryptPasswordEncoder");
    }

    @Test
    @DisplayName("Debería crear un AuthenticationManager correctamente")
    void should_Create_AuthenticationManager() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AuthenticationManager authenticationManager = webSecurityConfig.authenticationManager(userDetailsService, passwordEncoder);

        assertNotNull(authenticationManager, "El AuthenticationManager no debería ser nulo");
        assertTrue(authenticationManager instanceof ProviderManager, "El AuthenticationManager debería ser una instancia de ProviderManager");
       
        ProviderManager providerManager = (ProviderManager) authenticationManager;
        assertEquals(1, providerManager.getProviders().size(), "Debe haber exactamente un AuthenticationProvider");
        assertTrue(providerManager.getProviders().get(0) instanceof DaoAuthenticationProvider, "El proveedor debe ser DaoAuthenticationProvider");
    }
}

