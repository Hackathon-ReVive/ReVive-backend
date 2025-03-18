package com.revive.marketplace.product;

import java.math.BigDecimal;

public class ProductRequestDTO {
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private ProductCategory category;
    private ProductStatus status;
    private boolean liked;
    private Long userId;

    public ProductRequestDTO() {}

    public ProductRequestDTO(String title, String description, BigDecimal price, String image,
                             ProductCategory category, ProductStatus status, boolean liked, Long userId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.status = status;
        this.liked = liked;
        this.userId = userId;
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public ProductCategory getCategory() { return category; }
    public ProductStatus getStatus() { return status; }
    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}

