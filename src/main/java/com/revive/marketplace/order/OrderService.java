package com.revive.marketplace.order;

import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.product.ProductRepository;
import com.revive.marketplace.user.User;
import com.revive.marketplace.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    
    // 1. Obtener todas las órdenes
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    // 2. Obtener una orden por ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    // 3. Crear una nueva orden
    public Order createOrder(Order order) {

        ProductModel product = productRepository.findById(order.getProduct().getId())
              .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        
  
        order.setProduct(product);
   
        return orderRepository.save(order);
    }
    
    // 4. Actualizar una orden
    public Optional<Order> updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(existingOrder -> {
            existingOrder.setDateOrder(updatedOrder.getDateOrder());
            existingOrder.setProduct(updatedOrder.getProduct());
            existingOrder.setStatus(updatedOrder.getStatus());
            existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
            existingOrder.setAddress(updatedOrder.getAddress());
            return orderRepository.save(existingOrder);
        });
    }
    
    //  5. Eliminar una orden
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
