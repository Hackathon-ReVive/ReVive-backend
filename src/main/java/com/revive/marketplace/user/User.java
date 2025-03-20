package com.revive.marketplace.user;

import com.revive.marketplace.cart.Cart;
import com.revive.marketplace.order.Order;
import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.role.Role;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = true)
    private String phonenumber;
    
    @Column(nullable = false)
    private String address;
    
    // Option 1: Keep ManyToMany relationship with external Role entity
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
          name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
    // Option 2: Use simple enum approach
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;
    
    // Simple enum directly in User class
    public enum UserRole {
        USER, ADMIN, SELLER
    }
    
    // Relación con productos (un usuario puede tener varios productos)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductModel> products;
    
    // Relación con órdenes (un usuario puede tener varias órdenes)
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;
    
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
    
    // Constructor vacío requerido por Hibernate
    public User() {
    }
    
    public User(Long id, String username, String password, String email, String phonenumber, String address, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.address = address;
        this.role = role;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhonenumber() { return phonenumber; }
    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
    
    public List<ProductModel> getProducts() { return products; }
    public void setProducts(List<ProductModel> products) { this.products = products; }
    
    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}