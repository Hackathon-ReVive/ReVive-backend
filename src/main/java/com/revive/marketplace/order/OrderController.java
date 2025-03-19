package com.revive.marketplace.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderService orderService;
    
    // Constructor con inyección de dependencias
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    //  1. Obtener todas las órdenes
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    // 2. Obtener una orden por ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
    }
    
    // 3. Crear una nueva orden
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order newOrder = orderService.createOrder(order);
        return ResponseEntity.ok(newOrder);
    }
    
    // 4. Actualizar una orden
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        return orderService.updateOrder(id, updatedOrder)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
    }
    
    // 5. Eliminar una orden
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        boolean deleted = orderService.deleteOrder(id);
        if (deleted) {
            return ResponseEntity.ok("Order deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
