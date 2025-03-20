package com.revive.marketplace.order;

import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.product.ProductRepository;
import com.revive.marketplace.product.ProductStatus;
import com.revive.marketplace.user.User;
import com.revive.marketplace.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
              .map(OrderDTO::new)
              .collect(Collectors.toList());
    }
    
    public Optional<OrderDTO> getOrderById(Long id) {
        return orderRepository.findById(id).map(OrderDTO::new);
    }
    
    @Transactional
    public OrderDTO createOrder(Long buyerId, Long productId, String address) {
        if (buyerId == null || productId == null || address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los datos de la orden deben estar completos");
        }
        
        User buyer = userRepository.findById(buyerId)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        ProductModel product = productRepository.findById(productId)
              .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        if (!product.getStatus().equals(ProductStatus.AVAILABLE)) {
            throw new RuntimeException("El producto no está disponible para la compra");
        }
        
        Order order = new Order(buyer, product, OrderStatus.PENDING, product.getPrice(), address);
        orderRepository.save(order);
        return new OrderDTO(order);
    }
    
    @Transactional
    public Optional<OrderDTO> updateOrder(Long id, OrderRequestDTO request) {
        return orderRepository.findById(id).map(existingOrder -> {
            existingOrder.setStatus(request.getStatus());
            existingOrder.setTotalPrice(new BigDecimal(request.getTotalPrice().toString()));
            existingOrder.setAddress(request.getAddress());
            orderRepository.save(existingOrder);
            return new OrderDTO(existingOrder);
        });
    }
    
    @Transactional
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
