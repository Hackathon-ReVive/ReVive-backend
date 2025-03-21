package com.revive.marketplace.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private ProductCategory category;
    private ProductStatus status;
    private boolean liked;
    private LocalDateTime createdAt;
    private Long userId;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String title, String description, BigDecimal price, String image,
            ProductCategory category, ProductStatus status, boolean liked,
            LocalDateTime createdAt, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.status = status;
        this.liked = liked;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public ProductDTO(ProductModel product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.image = product.getImage();
        this.category = product.getCategory();
        this.status = product.getStatus();
        this.liked = product.isLiked();
        this.createdAt = product.getCreatedAt();
        this.userId = product.getUser().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
