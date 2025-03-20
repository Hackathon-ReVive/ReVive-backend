package com.revive.marketplace.cart;

import com.revive.marketplace.product.ProductModel;
import com.revive.marketplace.product.ProductRepository;
import com.revive.marketplace.user.User;
import com.revive.marketplace.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    
    @Transactional
    public Cart getOrCreateCart(Long userId) {
        User user = userRepository.findById(userId)
              .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart(user);
            user.setCart(cart);
            cartRepository.save(cart);
        }
        return cart;
    }
    
    @Transactional
    public CartItem addItemToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);
        ProductModel product = productRepository.findById(productId)
              .orElseThrow(() -> new RuntimeException("Product not found"));
        
        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            return cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem(product, quantity);
            cart.addItem(newItem);
            return cartItemRepository.save(newItem);
        }
    }
    
    @Transactional
    public void removeItemFromCart(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        if (item != null) {
            cart.removeItem(item);
            cartItemRepository.delete(item);
        }
    }
    
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}