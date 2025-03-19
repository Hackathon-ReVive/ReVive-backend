package com.revive.marketplace.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class OrderDTOTest {

    private Order order;
    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        // Simulación de un comprador
        User buyer = new User();
        buyer.setId(1L);

        // Simulación de un producto
        ProductModel product = new ProductModel();
      

        // Simulación de una orden
        order = new Order();
        order.setDateOrder(LocalDateTime.now());
        order.setBuyer(buyer);
        order.setProduct(product);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(new BigDecimal("99.99"));
        order.setAddress("123 Market Street");

        // Creación del DTO
        orderDTO = new OrderDTO(order);
    }

    @Test
    @DisplayName("Debe crear correctamente un OrderDTO desde un Order")
    void should_Create_OrderDTO_Correctly() {
        assertNotNull(orderDTO, "El OrderDTO no debe ser nulo");
        assertEquals(order.getId(), orderDTO.getId(), "El ID debe coincidir");
        assertEquals(order.getDateOrder(), orderDTO.getDateOrder(), "La fecha de la orden debe coincidir");
        assertEquals(order.getBuyer().getId(), orderDTO.getBuyerId(), "El ID del comprador debe coincidir");
        assertEquals(order.getProduct().getId(), orderDTO.getProductId(), "El ID del producto debe coincidir");
        assertEquals(order.getStatus(), orderDTO.getStatus(), "El estado de la orden debe coincidir");
        assertEquals(order.getTotalPrice(), orderDTO.getTotalPrice(), "El precio total debe coincidir");
        assertEquals(order.getAddress(), orderDTO.getAddress(), "La dirección debe coincidir");
    }

    @Test
    @DisplayName("Debe lanzar NullPointerException si el Order es nulo")
    void should_Throw_NullPointerException_If_Order_Is_Null() {
        Exception exception = assertThrows(NullPointerException.class, () -> new OrderDTO(null));
        assertEquals("Cannot invoke \"com.revive.marketplace.order.Order.getId()\" because \"order\" is null", exception.getMessage());
    }
}


