package com.revive.marketplace.order;

import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductModel product;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
    
    @Column(nullable = false)
    private BigDecimal totalPrice;
    
    @Column(nullable = false)
    private String address;
    
    private LocalDateTime dateOrder;
    
    public Order() {}
    
    public Order(User buyer, ProductModel product, OrderStatus status, BigDecimal totalPrice, String address) {
        this.buyer = buyer;
        this.product = product;
        this.status = status;
        this.totalPrice = totalPrice;
        this.address = address;
        this.dateOrder = LocalDateTime.now();
    }
    
    public Long getId() {
        return id;
    }
    
    public User getBuyer() {
        return buyer;
    }
    
    public ProductModel getProduct() {
        return product;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public String getAddress() {
        return address;
    }
    
    public LocalDateTime getDateOrder() {
        return dateOrder;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
}
