package com.revive.marketplace.Order;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.hibernate.mapping.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        if (orderRequest.getBuyerId() == null || orderRequest.getProductId() == null || orderRequest.getAddress() == null || orderRequest.getAddress().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Todos los datos de la orden deben estar completos.");
        }
        OrderDTO order = orderService.createOrder(orderRequest.getBuyerId(), orderRequest.getProductId(), orderRequest.getAddress());
        return ResponseEntity.ok(order);
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO request) {
        return orderService.updateOrder(id, request)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        if (orderService.deleteOrder(id)) {
            return ResponseEntity.ok("Order deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}

