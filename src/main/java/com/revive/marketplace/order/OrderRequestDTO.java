package com.revive.marketplace.order;

import java.math.BigDecimal;

public class OrderRequestDTO {
    private Long buyerId;
    private Long productId;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private String address;
    
    public Long getBuyerId() {
        return buyerId;
    }
    
    public Long getProductId() {
        return productId;
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

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
