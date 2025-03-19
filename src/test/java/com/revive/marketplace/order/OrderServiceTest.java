package com.revive.marketplace.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.product.ProductRepository;
import com.revive.marketplace.user.User;
import com.revive.marketplace.user.UserRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private OrderRepository orderRepository;
    @Mock private UserRepository userRepository;
    @Mock private ProductRepository productRepository;
    
    @InjectMocks private OrderService orderService;
    
    private User buyer;
    private ProductModel product;
    private Order order;

    @BeforeEach
    void setUp() {
        buyer = new User();
        buyer.setId(1L);

        product = new ProductModel();
        product.setId(10L);

        order = new Order(buyer, product, OrderStatus.PENDING, new BigDecimal("99.99"), "123 Main St");
    }

    @Test
    @DisplayName("Debe crear una orden correctamente")
    void should_Create_Order_Successfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO createdOrder = orderService.createOrder(1L, 10L, "123 Main St");

        assertNotNull(createdOrder);
        assertEquals(buyer, createdOrder.getBuyerId());
        assertEquals(product, createdOrder.getProductId());
        assertEquals(OrderStatus.PENDING, createdOrder.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el usuario no existe")
    void should_Throw_Exception_When_User_Not_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.createOrder(1L, 10L, "123 Main St"));
    }
}

