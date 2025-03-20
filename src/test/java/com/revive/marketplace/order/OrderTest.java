package com.revive.marketplace.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.user.User;

public class OrderTest {
    private Order order;
    private User mockUser;
    private ProductModel mockProduct;

    @BeforeEach
    void setUp() {
        mockUser = Mockito.mock(User.class);  // Мок для User
        mockProduct = Mockito.mock(ProductModel.class);  // Мок для ProductModel
        order = new Order(mockUser, mockProduct, OrderStatus.PENDING, BigDecimal.valueOf(100), "123 Address");
    }

    @Test
    void testSetAddress() {
        String newAddress = "456 New Address";
        order.setAddress(newAddress);
        assertEquals(newAddress, order.getAddress(), "Address should be updated.");
    }

    @Test
    void testSetBuyer() {
        User newBuyer = Mockito.mock(User.class);  // Мок для нового покупця
        order.setBuyer(newBuyer);
        assertEquals(newBuyer, order.getBuyer(), "Buyer should be updated.");
    }

    @Test
    void testSetDateOrder() {
        LocalDateTime newDate = LocalDateTime.of(2025, 3, 20, 10, 0, 0, 0);
        order.setDateOrder(newDate);
        assertEquals(newDate, order.getDateOrder(), "Date of order should be updated.");
    }

    @Test
    void testSetProduct() {
        ProductModel newProduct = Mockito.mock(ProductModel.class);  // Мок для нового продукту
        order.setProduct(newProduct);
        assertEquals(newProduct, order.getProduct(), "Product should be updated.");
    }

    @Test
    void testSetTotalPrice() {
        BigDecimal newPrice = BigDecimal.valueOf(200);
        order.setTotalPrice(newPrice);
        assertEquals(newPrice, order.getTotalPrice(), "Total price should be updated.");
    }
}
