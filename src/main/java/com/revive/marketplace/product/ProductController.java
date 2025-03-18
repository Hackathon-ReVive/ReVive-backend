package com.revive.marketplace.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
   
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Crear un producto con validación
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductRequestDTO requestDTO) {
        ProductDTO product = productService.createProduct(requestDTO);
        return ResponseEntity.ok(product);
    }

    // Obtener un producto por ID con validación
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable @NotNull Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Obtener productos por categoría con validación
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable @NotNull ProductCategory category) {
        List<ProductDTO> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    // Obtener productos por estado con validación
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductDTO>> getProductsByStatus(@PathVariable @NotNull ProductStatus status) {
        List<ProductDTO> products = productService.getProductsByStatus(status);
        return ResponseEntity.ok(products);
    }

    // Actualizar un producto con validación
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable @NotNull Long id,
                                                     @RequestBody @Valid ProductRequestDTO requestDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, requestDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    // Eliminar un producto con validación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @NotNull Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}