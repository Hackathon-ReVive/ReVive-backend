package com.revive.marketplace.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        
        return new ProviderManager(authProvider);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
              .csrf(csrf -> csrf.disable()) // 🔥 Deshabilitar CSRF para APIs REST
              .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 🔥 API sin sesiones
              .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/", "/api/users/login", "/api/users/register").permitAll()
                    .requestMatchers("/api/users/**").hasRole("ADMIN") // 👈 Cambiado a hasRole
                    .requestMatchers("/api/products/**", "/api/orders/**").hasAnyRole("ADMIN", "USER") // 👈 Cambiado a hasAnyRole
                    .anyRequest().authenticated()
              )
              .httpBasic(Customizer.withDefaults()) // ✅ Habilitar autenticación básica correctamente
              .formLogin(form -> form.disable())
              .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/api/users/login?logout")
                    .permitAll()
              );
        
        return http.build();
    }
}