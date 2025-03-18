package com.revive.marketplace.Order;

import java.time.LocalDateTime;

import com.revive.marketplace.product.ProductModel;

public record OrderDto(Long id, LocalDateTime dateOrder, Long userId, Long productId,  OrderStatus status, Double totalPrice,
        String address) {
    
}
