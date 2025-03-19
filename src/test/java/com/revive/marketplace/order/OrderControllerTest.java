package com.revive.marketplace.order;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.user.User;

@WebMvcTest(OrderController.class)
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Autowired private MockMvc mockMvc;
    
    @MockitoBean private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        User buyer = new User();
        buyer.setId(1L);

        ProductModel product = new ProductModel();
        product.setId(10L);

        order = new Order(buyer, product, OrderStatus.PENDING, new BigDecimal("99.99"), "123 Main St");
    }

    @Test
    @DisplayName("Debe crear una orden y devolver 201 CREATED")
    void should_Create_Order_And_Return_Created() throws Exception {
        OrderDTO orderDTO = new OrderDTO(order);
        when(orderService.createOrder(anyLong(), anyLong(), anyString())).thenReturn(orderDTO);

        mockMvc.perform(post("/api/orders")
                .param("buyerId", "1")
                .param("productId", "10")
                .param("address", "123 Main St"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.totalPrice").value("99.99"));

        verify(orderService, times(1)).createOrder(anyLong(), anyLong(), anyString());
    }
}

