package com.revive.marketplace.product;

import com.revive.marketplace.user.User;
import com.revive.marketplace.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    
    @Transactional
    public ProductDto createProduct(ProductRequestDTO requestDTO) {
        if (requestDTO.getUserId() == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        
        System.out.println("userId recibido: " + requestDTO.getUserId());
        System.out.println("title recibido: " + requestDTO.getTitle());
        System.out.println("description recibido: " + requestDTO.getDescription());
        System.out.println("price recibido: " + requestDTO.getPrice());
        System.out.println("category recibido: " + requestDTO.getCategory());
        System.out.println("status recibido: " + requestDTO.getStatus());
        
        User user = userRepository.findById(requestDTO.getUserId())
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la BD"));
        
        ProductModel product = new ProductModel(
              requestDTO.getTitle(),
              requestDTO.getDescription(),
              requestDTO.getPrice(),
              requestDTO.getImage(),
              requestDTO.getCategory(),
              requestDTO.getStatus(),
              requestDTO.isLiked(),
              user
        );
        
        System.out.println("Producto creado: " + product.toString());
        
        productRepository.save(product);
        return new ProductDto(product);
    }
    
    
    
    public ProductDto getProductById(Long id) {
        ProductModel product = productRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return new ProductDto(product);
    }
    
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
              .map(ProductDto::new)
              .collect(Collectors.toList());
    }
    
    public List<ProductDto> getProductsByCategory(ProductCategory category) {
        return productRepository.findByCategory(category).stream()
              .map(ProductDto::new)
              .collect(Collectors.toList());
    }
    
    public List<ProductDto> getProductsByStatus(ProductStatus status) {
        return productRepository.findByStatus(status).stream()
              .map(ProductDto::new)
              .collect(Collectors.toList());
    }
    
    @Transactional
    public ProductDto updateProduct(Long id, ProductRequestDTO requestDTO, String userEmail) {
        ProductModel product = productRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        if (!product.getUser().getEmail().equals(userEmail)) {
            throw new SecurityException("No tienes permiso para modificar este producto");
        }
        
        product.setTitle(requestDTO.getTitle());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setImage(requestDTO.getImage());
        product.setCategory(requestDTO.getCategory());
        product.setStatus(requestDTO.getStatus());
        product.setLiked(requestDTO.isLiked());
        
        productRepository.save(product);
        return new ProductDto(product);
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
