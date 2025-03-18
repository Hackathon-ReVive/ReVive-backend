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
@RequestMapping("/products")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Todos los métodos requieren autenticación
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ✅ Crear un producto (Solo usuarios autenticados)
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductRequestDTO requestDTO) {
        ProductDTO product = productService.createProduct(requestDTO);
        return ResponseEntity.ok(product);
    }

    // ✅ Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable @NotNull Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // ✅ Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // ✅ Obtener productos por categoría
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable @NotNull ProductCategory category) {
        List<ProductDTO> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    // ✅ Obtener productos por estado
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductDTO>> getProductsByStatus(@PathVariable @NotNull ProductStatus status) {
        List<ProductDTO> products = productService.getProductsByStatus(status);
        return ResponseEntity.ok(products);
    }

    // ✅ Actualizar un producto (Solo el dueño del producto puede hacerlo)
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable @NotNull Long id,
            @RequestBody @Valid ProductRequestDTO requestDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername(); // Obtener email del usuario autenticado
        ProductDTO updatedProduct = productService.updateProduct(id, requestDTO, userEmail);
        return ResponseEntity.ok(updatedProduct);
    }

    // ✅ Eliminar un producto (Solo los ADMIN pueden eliminar productos)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable @NotNull Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
