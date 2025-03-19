package com.revive.marketplace.order;

import java.time.LocalDateTime;

public record OrderDto(Long id, LocalDateTime dateOrder, Long userId, Long productId,  OrderStatus status, Double totalPrice,
        String address) {
    
}
