package com.revive.marketplace.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig {
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
              .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                    .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**", "/error").permitAll()  // Acceso público
                    .requestMatchers("/api/auth/**").permitAll() // Acceso público a la autenticación
                    .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN") // Solo ADMIN puede acceder a '/api/users'
                    .requestMatchers("/api/products/**").permitAll()

                    .anyRequest().authenticated()  // El resto de las rutas requieren autenticación
              )
              .formLogin(formLogin -> formLogin
                    .loginPage("/login") // Ruta para la página de login personalizada
                    .loginProcessingUrl("/process-login") // URL de procesamiento de login
                    .defaultSuccessUrl("/dashboard", true) // Redirige a dashboard después de login exitoso
                    .failureUrl("/login?error=true") // Redirige a login si la autenticación falla
                    .permitAll() // Asegura que el login sea accesible públicamente
              )
              .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL para el logout
                    .logoutSuccessUrl("/login?logout") // Redirige a login después de logout
                    .permitAll() // Asegura que el logout sea accesible públicamente
              )
              .cors(cors -> cors.disable()) // Deshabilita CORS (si no lo necesitas)
              .csrf(csrf -> csrf.disable()); // Deshabilita CSRF (si estás trabajando solo con API REST)
        
        return http.build();
    }
}
