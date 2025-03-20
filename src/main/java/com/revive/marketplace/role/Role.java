package com.revive.marketplace.role;

import com.revive.marketplace.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    // Fix the mappedBy to match the actual field name in User entity
    // or remove mappedBy if you're using a @JoinTable in User
    @ManyToMany(mappedBy = "roles")  // Ensure this matches the field name in User
    private Set<User> users = new HashSet<>();
    
    public enum Type {
        USER, ADMIN, SELLER
    }
}