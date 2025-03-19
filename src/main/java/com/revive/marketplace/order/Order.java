package com.revive.marketplace.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date_order", nullable = false)
    private LocalDateTime dateOrder;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private ProductModel product;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;
    
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;
    
    @Column(name = "address", nullable = false)
    private String address;
    
    public Order() {
    }
    
    public Order(LocalDateTime dateOrder, User user, ProductModel product, OrderStatus status, Double totalPrice, String address) {
        this.dateOrder = dateOrder;
        this.user = user;
        this.product = product;
        this.status = status;
        this.totalPrice = totalPrice;
        this.address = address;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public LocalDateTime getDateOrder() { return dateOrder; }
    public User getUser() { return user; }
    public ProductModel getProduct() { return product; }
    public OrderStatus getStatus() { return status; }
    public Double getTotalPrice() { return totalPrice; }
    public String getAddress() { return address; }
}
