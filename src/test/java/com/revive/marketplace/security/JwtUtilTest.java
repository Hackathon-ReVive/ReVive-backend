package com.revive.marketplace.security;

import com.revive.marketplace.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private User mockUser;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setRole(User.UserRole.USER);
    }

    @Test
    @DisplayName("Should generate a valid JWT token")
    void should_Generate_Valid_Jwt_Token() {
        String token = jwtUtil.generateToken(mockUser);
        assertNotNull(token, "Generated token should not be null");
        assertFalse(token.isEmpty(), "Generated token should not be empty");
    }

    @Test
    @DisplayName("Should parse and validate JWT token successfully")
    void should_Parse_And_Validate_Jwt_Token_Successfully() {
        String token = jwtUtil.generateToken(mockUser);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey("verySecureKeyForDemoThatShouldBeLongAndComplex123!@#".getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertNotNull(claims, "Parsed claims should not be null");
        assertEquals("test@example.com", claims.getSubject(), "Email should match");
        assertEquals(1L, claims.get("id", Integer.class).longValue(), "User ID should match");
        assertEquals("USER", claims.get("role"), "User role should match");
    }
}
