package com.revive.marketplace.product;

import com.revive.marketplace.user.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, length = 500)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(nullable = false)
    private String image;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;
    
    @Column(nullable = false)
    private boolean liked;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public ProductModel() {
    }
    
    public ProductModel(String title, String description, BigDecimal price, String image,
                        ProductCategory category, ProductStatus status, boolean liked, User user) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.status = status;
        this.liked = liked;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }
    
    
    // Getters y Setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getImage() { return image; }
    public ProductCategory getCategory() { return category; }
    public ProductStatus getStatus() { return status; }
    public boolean isLiked() { return liked; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public User getUser() { return user; }
    
    public void setUser(User user) { this.user = user; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setImage(String image) { this.image = image; }
    public void setCategory(ProductCategory category) { this.category = category; }
    public void setStatus(ProductStatus status) { this.status = status; }
    public void setLiked(boolean liked) { this.liked = liked; }
    
    @Override
    public String toString() {
        return "ProductModel{" +
              "id=" + id +
              ", title='" + title + '\'' +
              ", description='" + description + '\'' +
              ", price=" + price +
              ", image='" + image + '\'' +
              ", category=" + category +
              ", status=" + status +
              ", liked=" + liked +
              ", createdAt=" + createdAt +
              ", user=" + (user != null ? user.getId() : "null") +
              '}';
    }
    
}

