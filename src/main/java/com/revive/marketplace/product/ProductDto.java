package com.revive.marketplace.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDto {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private ProductCategory category;
    private ProductStatus status;
    private boolean liked;
    private LocalDateTime createdAt;
    private long userId;

    public ProductDto(Long id, String title, String description, BigDecimal price, String image,
            ProductCategory category, ProductStatus status, boolean liked, LocalDateTime createdAt, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        // this.status = status;
        this.liked = liked;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }    
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getImage() { return image; }
    public ProductCategory getCategory() { return category; }
    public ProductStatus getStatus() { return status; }
    public boolean isLiked() { return liked; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getUserId() { return userId; }

}



