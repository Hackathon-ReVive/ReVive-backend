package com.revive.marketplace.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO {
    private Long id;
    private LocalDateTime dateOrder;
    private Long buyerId;
    private Long productId;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private String address;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.dateOrder = order.getDateOrder();
        this.buyerId = order.getBuyer().getId();
        this.productId = order.getProduct().getId();
        this.status = order.getStatus();
        this.totalPrice = order.getTotalPrice();
        this.address = order.getAddress();
    }

    // Nuevo Constructor para usar en los tests
    public OrderDTO(Long id, LocalDateTime dateOrder, Long buyerId, Long productId,
            OrderStatus status, BigDecimal totalPrice, String address) {
        this.id = id;
        this.dateOrder = dateOrder;
        this.buyerId = buyerId;
        this.productId = productId;
        this.status = status;
        this.totalPrice = totalPrice;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateOrder() {
        return dateOrder;
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
