package com.revive.marketplace.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemDTOTest {

    private CartItemDTO cartItem;

    @BeforeEach
    void setUp() {
        cartItem = new CartItemDTO();
    }

    @Test
    void should_SetAndGet_IdCorrectly() {
        cartItem.setId(1L);
        assertEquals(1L, cartItem.getId());
    }

    @Test
    void should_SetAndGet_ProductIdCorrectly() {
        cartItem.setProductId(100L);
        assertEquals(100L, cartItem.getProductId());
    }

    @Test
    void should_SetAndGet_ProductNameCorrectly() {
        cartItem.setProductName("Laptop");
        assertEquals("Laptop", cartItem.getProductName());
    }

    @Test
    void should_SetAndGet_ProductImageCorrectly() {
        cartItem.setProductImage("image.jpg");
        assertEquals("image.jpg", cartItem.getProductImage());
    }

    @Test
    void should_SetAndGet_ProductPriceCorrectly() {
        cartItem.setProductPrice(599.99);
        assertEquals(599.99, cartItem.getProductPrice());
    }

    @Test
    void should_SetAndGet_QuantityCorrectly() {
        cartItem.setQuantity(2);
        assertEquals(2, cartItem.getQuantity());
    }

    @Test
    void should_SetAndGet_SubtotalCorrectly() {
        cartItem.setSubtotal(1199.98);
        assertEquals(1199.98, cartItem.getSubtotal());
    }
}
