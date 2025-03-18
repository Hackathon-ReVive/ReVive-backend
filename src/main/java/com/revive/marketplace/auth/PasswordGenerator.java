package com.revive.marketplace.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode("admin123");
        System.out.println("Contraseña encriptada: " + encryptedPassword);
    }
}
