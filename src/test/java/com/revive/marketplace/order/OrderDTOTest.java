package com.revive.marketplace.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class OrderDTOTest {

    private OrderDTO orderDTOFromOrder;
    private OrderDTO orderDTOFromConstructor;
    private LocalDateTime dateOrder;
    private User mockUser;
    private ProductModel mockProduct;
    private Order mockOrder;
    private OrderDTO orderDTO;

@BeforeEach
void setUp() {
    dateOrder = LocalDateTime.now();

    mockUser = mock(User.class);
    when(mockUser.getId()).thenReturn(2L);

    mockProduct = mock(ProductModel.class);
    when(mockProduct.getId()).thenReturn(3L);

    mockOrder = mock(Order.class);
    when(mockOrder.getId()).thenReturn(1L);
    when(mockOrder.getDateOrder()).thenReturn(dateOrder);
    when(mockOrder.getBuyer()).thenReturn(mockUser);
    when(mockOrder.getProduct()).thenReturn(mockProduct);
    when(mockOrder.getStatus()).thenReturn(OrderStatus.PENDING);
    when(mockOrder.getTotalPrice()).thenReturn(new BigDecimal("100.00"));
    when(mockOrder.getAddress()).thenReturn("123 Test Street");

    orderDTO = new OrderDTO(mockOrder);
}


@Test
@DisplayName("Debe crear OrderDTO desde una Order correctamente")
void should_Create_OrderDTO_From_Order() {
    assertNotNull(orderDTO, "El DTO no debe ser nulo");
    assertNotNull(orderDTO.getId(), "El ID no debe ser nulo");
    assertNotNull(orderDTO.getBuyerId(), "El Buyer ID no debe ser nulo");
    assertNotNull(orderDTO.getProductId(), "El Product ID no debe ser nulo");
    assertNotNull(orderDTO.getDateOrder(), "La fecha no debe ser nula");
    assertNotNull(orderDTO.getTotalPrice(), "El precio total no debe ser nulo");
    assertNotNull(orderDTO.getAddress(), "La dirección no debe ser nula");

    assertEquals(mockOrder.getId(), orderDTO.getId());
    assertEquals(mockOrder.getBuyer().getId(), orderDTO.getBuyerId());
    assertEquals(mockOrder.getProduct().getId(), orderDTO.getProductId());
    assertEquals(mockOrder.getStatus(), orderDTO.getStatus());
    assertEquals(mockOrder.getTotalPrice(), orderDTO.getTotalPrice());
    assertEquals(mockOrder.getAddress(), orderDTO.getAddress());
}

@Test
@DisplayName("Debe crear OrderDTO correctamente usando el nuevo constructor")
void should_Create_OrderDTO_From_Constructor() {
    assertNotNull(orderDTOFromConstructor, "El OrderDTO no debería ser nulo");
    
    assertEquals(1L, orderDTOFromConstructor.getId(), "El ID no coincide");
    assertEquals(2L, orderDTOFromConstructor.getBuyerId(), "El ID del comprador no coincide");
    assertEquals(3L, orderDTOFromConstructor.getProductId(), "El ID del producto no coincide");
    assertEquals(OrderStatus.PENDING, orderDTOFromConstructor.getStatus(), "El estado no coincide");
    assertEquals(new BigDecimal("100.00"), orderDTOFromConstructor.getTotalPrice(), "El precio total no coincide");
    assertEquals("123 Test Street", orderDTOFromConstructor.getAddress(), "La dirección no coincide");
    
    assertTrue(dateOrder.isEqual(orderDTOFromConstructor.getDateOrder()),
            "Las fechas deben coincidir sin diferencias de precisión");
}


    @Test
    @DisplayName("Debe devolver valores correctos con los getters")
    void should_Return_Correct_Values_From_Getters() {
        assertEquals(1L, orderDTOFromConstructor.getId());
        assertEquals(dateOrder, orderDTOFromConstructor.getDateOrder());
        assertEquals(2L, orderDTOFromConstructor.getBuyerId());
        assertEquals(3L, orderDTOFromConstructor.getProductId());
        assertEquals(OrderStatus.PENDING, orderDTOFromConstructor.getStatus());
        assertEquals(new BigDecimal("100.00"), orderDTOFromConstructor.getTotalPrice());
        assertEquals("123 Test Street", orderDTOFromConstructor.getAddress());
    }
}

