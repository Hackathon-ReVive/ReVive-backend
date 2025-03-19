package com.revive.marketplace.product;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Todos los métodos requieren autenticación
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Crear un producto (Solo usuarios autenticados)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductRequestDTO requestDTO) {
        ProductDto product = productService.createProduct(requestDTO);
        return ResponseEntity.ok(product);
    }
    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable @NotNull Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Obtener productos por categoría
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable @NotNull ProductCategory category) {
        List<ProductDto> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    //  Obtener productos por estado
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductDto>> getProductsByStatus(@PathVariable @NotNull ProductStatus status) {
        List<ProductDto> products = productService.getProductsByStatus(status);
        return ResponseEntity.ok(products);
    }

    //  Actualizar un producto (Solo el dueño del producto puede hacerlo)
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable @NotNull Long id,
            @RequestBody @Valid ProductRequestDTO requestDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername(); // Obtener email del usuario autenticado
        ProductDto updatedProduct = productService.updateProduct(id, requestDTO, userEmail);
        return ResponseEntity.ok(updatedProduct);
    }

    // Eliminar un producto (Solo los ADMIN pueden eliminar productos)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable @NotNull Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

