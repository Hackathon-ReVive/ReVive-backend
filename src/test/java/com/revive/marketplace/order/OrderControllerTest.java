package com.revive.marketplace.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revive.marketplace.product.ProductCategory;
import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.product.ProductStatus;
import com.revive.marketplace.user.User;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testGetAllOrders() {
        User buyer = new User(1L, "JohnDoe", "password", "john@example.com", "123456789", "123 Main St",
                User.UserRole.USER);
        ProductModel product = new ProductModel("Laptop", "High-end laptop", new BigDecimal("1200.00"), "image.jpg",
                ProductCategory.ELECTRONICS, ProductStatus.AVAILABLE, false, buyer);

        Order order1 = new Order(buyer, product, OrderStatus.PENDING, new BigDecimal("1200.00"), "123 Main St");
        Order order2 = new Order(buyer, product, OrderStatus.CONFIRMED, new BigDecimal("1300.00"), "456 Elm St");

        List<OrderDTO> orders = List.of(new OrderDTO(order1), new OrderDTO(order2));

        when(orderService.getAllOrders()).thenReturn(orders);

        ResponseEntity<List<OrderDTO>> response = orderController.getAllOrders();
        List<OrderDTO> responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());

        assertEquals(order1.getId(), responseBody.get(0).getId());

        verify(orderService).getAllOrders();
    }

    @Test
    void testGetOrderById() {
        Long orderId = 1L;
        User buyer = new User(1L, "JohnDoe", "password", "john@example.com", "123456789", "123 Main St",
                User.UserRole.USER);
        ProductModel product = new ProductModel("Laptop", "High-end laptop", new BigDecimal("1200.00"), "image.jpg",
                ProductCategory.ELECTRONICS, ProductStatus.AVAILABLE, false, buyer);
        Order order = new Order(buyer, product, OrderStatus.PENDING, new BigDecimal("1200.00"), "123 Main St");

        order.setId(orderId);

        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(new OrderDTO(order)));

        ResponseEntity<OrderDTO> response = orderController.getOrderById(orderId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(orderId, response.getBody().getId());

        verify(orderService).getOrderById(orderId);
    }

    @Test
    void testCreateOrder() throws Exception {
        Long buyerId = 1L;
        Long productId = 1L;
        String address = "123 Main St";

        OrderDTO orderDTO = new OrderDTO(
                new Order(new User(), new ProductModel(), OrderStatus.PENDING, new BigDecimal("1200.00"), address));

        OrderRequestDTO orderRequest = new OrderRequestDTO();
        orderRequest.setBuyerId(buyerId);
        orderRequest.setProductId(productId);
        orderRequest.setAddress(address);

        when(orderService.createOrder(buyerId, productId, address)).thenReturn(orderDTO);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderDTO.getId()))
                .andExpect(jsonPath("$.buyerId").value(orderDTO.getBuyerId()))
                .andExpect(jsonPath("$.productId").value(orderDTO.getProductId()))
                .andExpect(jsonPath("$.status").value(orderDTO.getStatus().toString()))
                .andExpect(jsonPath("$.address").value(orderDTO.getAddress()));

        verify(orderService).createOrder(buyerId, productId, address);
    }

    @Test
    void testDeleteOrder() throws Exception {
        Long orderId = 1L;

        when(orderService.deleteOrder(orderId)).thenReturn(true);

        mockMvc.perform(delete("/api/orders/" + orderId))
                .andExpect(status().isOk())
                .andExpect(content().string("Order deleted successfully"));

        verify(orderService).deleteOrder(orderId);
    }

    @Test
    void testDeleteOrderNotFound() throws Exception {
        Long orderId = 1L;

        when(orderService.deleteOrder(orderId)).thenReturn(false);

        mockMvc.perform(delete("/api/orders/" + orderId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        verify(orderService).deleteOrder(orderId);
    }

}
