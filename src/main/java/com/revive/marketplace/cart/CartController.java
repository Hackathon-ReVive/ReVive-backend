package com.revive.marketplace.cart;

import com.revive.marketplace.product.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{userId}/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    
    @GetMapping
    public ResponseEntity<CartDTO> getCart(@PathVariable Long userId) {
        Cart cart = cartService.getOrCreateCart(userId);
        return ResponseEntity.ok(convertToDTO(cart));
    }
    
    @PostMapping
    public ResponseEntity<CartDTO> addToCart(
          @PathVariable Long userId,
          @RequestParam Long productId,
          @RequestParam(defaultValue = "1") Integer quantity) {
        cartService.addItemToCart(userId, productId, quantity);
        Cart updatedCart = cartService.getOrCreateCart(userId);
        return ResponseEntity.ok(convertToDTO(updatedCart));
    }
    
    @DeleteMapping("/{productId}")
    public ResponseEntity<CartDTO> removeFromCart(
          @PathVariable Long userId,
          @PathVariable Long productId) {
        cartService.removeItemFromCart(userId, productId);
        Cart updatedCart = cartService.getOrCreateCart(userId);
        return ResponseEntity.ok(convertToDTO(updatedCart));
    }
    
    @DeleteMapping
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
    
    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        
        List<CartItemDTO> itemDTOs = cart.getItems().stream()
              .map(this::convertToItemDTO)
              .collect(Collectors.toList());
        
        dto.setItems(itemDTOs);
        dto.setTotalPrice(calculateTotal(itemDTOs));
        return dto;
    }
    
    private CartItemDTO convertToItemDTO(CartItem item) {
        ProductModel product = item.getProduct();
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setProductId(product.getId());
        dto.setProductName(product.getTitle());
        dto.setProductImage(product.getImage());
        
        // Convert BigDecimal to double
        dto.setProductPrice(product.getPrice().doubleValue());
        
        dto.setQuantity(item.getQuantity());
        
        // Proper multiplication with BigDecimal
        BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        dto.setSubtotal(subtotal.doubleValue());
        
        return dto;
    }
    private double calculateTotal(List<CartItemDTO> items) {
        return items.stream()
              .mapToDouble(CartItemDTO::getSubtotal)
              .sum();
    }
}