package com.revive.marketplace.product;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revive.marketplace.user.User;
import com.revive.marketplace.user.UserRepository;



@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    public ProductDTO createProduct(ProductRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductModel product = new ProductModel(
                null, requestDTO.getTitle(), requestDTO.getDescription(), requestDTO.getPrice(),
                requestDTO.getImage(), requestDTO.getCategory(), requestDTO.getStatus(),
                requestDTO.isLiked(), java.time.LocalDateTime.now(), user);
        product = productRepository.save(product);
        return new ProductDTO(product.getId(), product.getTitle(), product.getDescription(),
                product.getPrice(), product.getImage(), product.getCategory(), product.getStatus(),
                product.isLiked(), product.getCreatedAt(), product.getUser().getId());
    }

 
    public ProductDTO getProductById(Long id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductDTO(product.getId(), product.getTitle(), product.getDescription(),
                product.getPrice(), product.getImage(), product.getCategory(), product.getStatus(),
                product.isLiked(), product.getCreatedAt(), product.getUser().getId());
    }


    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductDTO(product.getId(), product.getTitle(), product.getDescription(),
                        product.getPrice(), product.getImage(), product.getCategory(), product.getStatus(),
                        product.isLiked(), product.getCreatedAt(), product.getUser().getId()))
                .collect(Collectors.toList());
    }

 
    public List<ProductDTO> getProductsByCategory(ProductCategory category) {
        return productRepository.findByCategory(category).stream()
                .map(product -> new ProductDTO(product.getId(), product.getTitle(), product.getDescription(),
                        product.getPrice(), product.getImage(), product.getCategory(), product.getStatus(),
                        product.isLiked(), product.getCreatedAt(), product.getUser().getId()))
                .collect(Collectors.toList());
    }


    public List<ProductDTO> getProductsByStatus(ProductStatus status) {
        return productRepository.findByStatus(status).stream()
                .map(product -> new ProductDTO(product.getId(), product.getTitle(), product.getDescription(),
                        product.getPrice(), product.getImage(), product.getCategory(), product.getStatus(),
                        product.isLiked(), product.getCreatedAt(), product.getUser().getId()))
                .collect(Collectors.toList());
    }

   
    public ProductDTO updateProduct(Long id, ProductRequestDTO requestDTO) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setTitle(requestDTO.getTitle());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setImage(requestDTO.getImage());
        product.setCategory(requestDTO.getCategory());
        product.setStatus(requestDTO.getStatus());
        product.setLiked(requestDTO.isLiked());

        product = productRepository.save(product);
        return new ProductDTO(product.getId(), product.getTitle(), product.getDescription(),
                product.getPrice(), product.getImage(), product.getCategory(), product.getStatus(),
                product.isLiked(), product.getCreatedAt(), product.getUser().getId());
    }

  
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }
}
