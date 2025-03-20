package com.revive.marketplace.order;

import java.math.BigDecimal;

public class OrderRequestDTO {
    private Long buyerId;
    private Long productId;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private String address;

    public OrderRequestDTO(long buyerId, long productId, OrderStatus status, BigDecimal totalPrice, String address) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.status = status;
        this.totalPrice = totalPrice;
        this.address = address;
    }

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
}
