package com.revive.marketplace.cart;

import com.revive.marketplace.product.ProductModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    private CartItem cartItem;
    private Cart cart;
    private ProductModel product;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        product = new ProductModel();
        cartItem = new CartItem(product, 2);
    }

    @Test
    void should_SetAndGet_IdCorrectly() {
        cartItem.setId(1L);
        assertEquals(1L, cartItem.getId());
    }

    @Test
    void should_SetAndGet_CartCorrectly() {
        cartItem.setCart(cart);
        assertEquals(cart, cartItem.getCart());
    }

    @Test
    void should_SetAndGet_ProductCorrectly() {
        assertEquals(product, cartItem.getProduct());
    }

    @Test
    void should_SetAndGet_QuantityCorrectly() {
        assertEquals(2, cartItem.getQuantity());
    }

    @Test
    void should_UpdateQuantityCorrectly() {
        cartItem.setQuantity(5);
        assertEquals(5, cartItem.getQuantity());
    }

    @Test
    void should_CreateCartItemWithValidConstructor() {
        CartItem newItem = new CartItem(product, 3);
        assertEquals(product, newItem.getProduct());
        assertEquals(3, newItem.getQuantity());
    }
}

