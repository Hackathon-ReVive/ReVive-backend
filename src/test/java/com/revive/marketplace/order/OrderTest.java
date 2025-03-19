package com.revive.marketplace.order;

import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderTest {

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
    void should_Create_Order_Correctly() {
        assertNotNull(order);
        assertEquals(buyer, order.getBuyer());
        assertEquals(product, order.getProduct());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(new BigDecimal("99.99"), order.getTotalPrice());
        assertEquals("123 Main St", order.getAddress());
        assertNotNull(order.getDateOrder());
    }

    @Test
    @DisplayName("Debe permitir cambiar el estado de la orden")
    void should_Update_Order_Status() {
        order.setStatus(OrderStatus.CONFIRMED);
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
    }

    @Test
    @DisplayName("Debe permitir actualizar la dirección de entrega")
    void should_Update_Order_Address() {
        order.setAddress("456 New St");
        assertEquals("456 New St", order.getAddress());
    }
}
