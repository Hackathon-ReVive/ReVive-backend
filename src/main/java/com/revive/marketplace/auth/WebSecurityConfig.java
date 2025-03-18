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
                    .requestMatchers("/", "/login", "/register", "/css/**", "/js/**",
                          "/images/**", "/error").permitAll()  // Permite rutas públicas
                    .requestMatchers("/api/auth/**").permitAll() // Acceso público a autenticación
                    .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN") // Solo ADMIN puede acceder
                    .requestMatchers("/api/products/**", "/api/orders/**").hasAnyAuthority(
                          "ROLE_ADMIN", "ROLE_USER") // ADMIN y USER pueden acceder
                    .anyRequest().authenticated()  // El resto de las rutas requiere autenticación
              )
              .formLogin(formLogin -> formLogin
                    .loginPage("/login")
                    .loginProcessingUrl("/process-login")
                    .defaultSuccessUrl("/dashboard", true)
                    .failureUrl("/login?error=true")
                    .permitAll()
              )
              .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
              )
              .cors(cors -> cors.disable())
              .csrf(csrf -> csrf.disable());
        
        return http.build();
    }
}
