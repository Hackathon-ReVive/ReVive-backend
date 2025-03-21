package com.revive.marketplace.order;

import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.product.ProductRepository;
import com.revive.marketplace.product.ProductStatus;
import com.revive.marketplace.user.User;
import com.revive.marketplace.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    private User buyer;
    private ProductModel product;
    private Order order;

    @BeforeEach
    void setUp() {
        buyer = new User();
        buyer.setId(1L);

        product = new ProductModel();
        product.setPrice(BigDecimal.valueOf(100.00));
        product.setStatus(ProductStatus.AVAILABLE);

        order = new Order(buyer, product, OrderStatus.PENDING, product.getPrice(), "123 Example Street");
    }

    @Test
    @DisplayName("Should retrieve all orders successfully")
    void should_Get_All_Orders_Successfully() {
        when(orderRepository.findAll()).thenReturn(List.of(order));
        List<OrderDTO> orders = orderService.getAllOrders();
        assertEquals(1, orders.size());
    }

    @Test
    @DisplayName("Should retrieve an order by ID successfully")
    void should_Get_Order_By_Id_Successfully() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Optional<OrderDTO> retrievedOrder = orderService.getOrderById(1L);
        assertTrue(retrievedOrder.isPresent());
    }

    @Test
    @DisplayName("Should create a new order successfully")
    void should_Create_Order_Successfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO createdOrder = orderService.createOrder(1L, 1L, "123 Example Street");
        assertNotNull(createdOrder);
        assertEquals(BigDecimal.valueOf(100.00), createdOrder.getTotalPrice());
    }

    @Test
    @DisplayName("Should update an existing order successfully")
    void should_Update_Order_Successfully() {
        OrderRequestDTO requestDTO = new OrderRequestDTO(1L, 1L, OrderStatus.CONFIRMED, BigDecimal.valueOf(120.00), "Updated Address");
        
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Optional<OrderDTO> updatedOrder = orderService.updateOrder(1L, requestDTO);
        assertTrue(updatedOrder.isPresent());
        assertEquals(OrderStatus.CONFIRMED, updatedOrder.get().getStatus());
    }

    @Test
    @DisplayName("Should delete an order successfully")
    void should_Delete_Order_Successfully() {
        when(orderRepository.existsById(1L)).thenReturn(true);
        doNothing().when(orderRepository).deleteById(1L);
        assertTrue(orderService.deleteOrder(1L));
    }
}
