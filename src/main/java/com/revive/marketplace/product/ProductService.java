package com.revive.marketplace.product;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import org.springframework.validation.annotation.Validated;


@Service
@Validated
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // ✅ Inyección de dependencias SIN @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public ProductDTO createProduct(@Valid @NotNull ProductRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ProductModel product = new ProductModel(
                requestDTO.getTitle(), requestDTO.getDescription(), requestDTO.getPrice(),
                requestDTO.getImage(), requestDTO.getCategory(), requestDTO.getStatus(),
                requestDTO.isLiked(), user
        );
        product = productRepository.save(product);
        return mapToDTO(product);
    }

    public ProductDTO getProductById(@NotNull Long id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return mapToDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategory(@NotNull ProductCategory category) {
        return productRepository.findByCategory(category).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByStatus(@NotNull ProductStatus status) {
        return productRepository.findByStatus(status).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO updateProduct(@NotNull Long id, @Valid @NotNull ProductRequestDTO requestDTO) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setTitle(requestDTO.getTitle());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setImage(requestDTO.getImage());
        product.setCategory(requestDTO.getCategory());
        product.setStatus(requestDTO.getStatus());
        product.setLiked(requestDTO.isLiked());

        product = productRepository.save(product);
        return mapToDTO(product);
    }

    public void deleteProduct(@NotNull Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found");
        }
        productRepository.deleteById(id);
    }

    private ProductDTO mapToDTO(ProductModel product) {
        return new ProductDTO(
                product.getId(), product.getTitle(), product.getDescription(),
                product.getPrice(), product.getImage(), product.getCategory(), product.getStatus(),
                product.isLiked(), product.getCreatedAt(), product.getUser().getId()
        );
    }
}
