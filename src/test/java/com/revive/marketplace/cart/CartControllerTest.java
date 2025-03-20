package com.revive.marketplace.cart;

import com.revive.marketplace.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    @DisplayName("Should return cart details when getCart is called")
    void should_ReturnCartDetails_When_GetCartIsCalled() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(new User());
        cart.setItems(Collections.emptyList());

        when(cartService.getOrCreateCart(anyLong())).thenReturn(cart);

        ResponseEntity<CartDTO> response = cartController.getCart(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        verify(cartService, times(1)).getOrCreateCart(1L);
    }

    @Test
    @DisplayName("Should add item to cart and return updated cart details")
    void should_AddItemToCart_When_AddToCartIsCalled() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(new User());
        cart.setItems(Collections.emptyList());

        when(cartService.getOrCreateCart(anyLong())).thenReturn(cart);

        ResponseEntity<CartDTO> response = cartController.addToCart(1L, 1L, 1);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        verify(cartService, times(1)).addItemToCart(1L, 1L, 1);
        verify(cartService, times(1)).getOrCreateCart(1L);
    }

    @Test
    @DisplayName("Should remove item from cart and return updated cart details")
    void should_RemoveItemFromCart_When_RemoveFromCartIsCalled() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(new User());
        cart.setItems(Collections.emptyList());

        when(cartService.getOrCreateCart(anyLong())).thenReturn(cart);

        ResponseEntity<CartDTO> response = cartController.removeFromCart(1L, 1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        verify(cartService, times(1)).removeItemFromCart(1L, 1L);
        verify(cartService, times(1)).getOrCreateCart(1L);
    }

    @Test
    @DisplayName("Should clear cart successfully")
    void should_ClearCart_When_ClearCartIsCalled() {
        doNothing().when(cartService).clearCart(anyLong());

        ResponseEntity<Void> response = cartController.clearCart(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(cartService, times(1)).clearCart(1L);
    }
}

