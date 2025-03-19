package com.revive.marketplace.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    
    // Buscar productos por categoría
    List<ProductModel> findByCategory(ProductCategory category);
    
    // Buscar productos por estado (disponible, vendido, reservado)
    List<ProductModel> findByStatus(ProductStatus status);
    
    // Buscar productos por usuario
    List<ProductModel> findByUserId(Long userId);
}
