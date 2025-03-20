package com.revive.marketplace.cart;

import com.revive.marketplace.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private Cart cart;
    private User user;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        cart = new Cart(user);
        cartItem = new CartItem();
    }

    @Test
    void should_SetAndGet_IdCorrectly() {
        cart.setId(1L);
        assertEquals(1L, cart.getId());
    }

    @Test
    void should_SetAndGet_UserCorrectly() {
        assertEquals(user, cart.getUser());
    }

    @Test
    void should_SetAndGet_ItemsCorrectly() {
        List<CartItem> items = List.of(new CartItem(), new CartItem());
        cart.setItems(items);
        assertEquals(2, cart.getItems().size());
    }

    @Test
    void should_AddItemToCart() {
        cart.addItem(cartItem);
        assertEquals(1, cart.getItems().size());
        assertEquals(cart, cartItem.getCart());
    }

    @Test
    void should_RemoveItemFromCart() {
        cart.addItem(cartItem);
        cart.removeItem(cartItem);

        assertEquals(0, cart.getItems().size());
        assertNull(cartItem.getCart());
    }

    @Test
    void should_InitializeWithEmptyItemList() {
        assertNotNull(cart.getItems());
        assertTrue(cart.getItems().isEmpty());
    }
}
