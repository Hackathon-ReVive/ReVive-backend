package com.revive.marketplace.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartDTOTest {

    private CartDTO cart;

    @BeforeEach
    void setUp() {
        cart = new CartDTO();
    }

    @Test
    void should_SetAndGet_IdCorrectly() {
        cart.setId(1L);
        assertEquals(1L, cart.getId());
    }

    @Test
    void should_SetAndGet_UserIdCorrectly() {
        cart.setUserId(2L);
        assertEquals(2L, cart.getUserId());
    }

    @Test
    void should_SetAndGet_ItemsCorrectly() {
        List<CartItemDTO> items = new ArrayList<>();
        cart.setItems(items);
        assertEquals(items, cart.getItems());
    }

    @Test
    void should_SetAndGet_TotalPriceCorrectly() {
        cart.setTotalPrice(100.0);
        assertEquals(100.0, cart.getTotalPrice());
    }

    @Test
    void should_AddItemToCart() {
        CartItemDTO item = new CartItemDTO();
        cart.getItems().add(item);
        
        assertEquals(1, cart.getItems().size());
        assertEquals(item, cart.getItems().get(0));
    }

    @Test
    void should_InitializeWithEmptyItemList() {
        assertNotNull(cart.getItems());
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    void should_ReplaceItemList() {
        List<CartItemDTO> newItems = new ArrayList<>();
        newItems.add(new CartItemDTO());
        cart.setItems(newItems);

        assertEquals(1, cart.getItems().size());
    }
}
