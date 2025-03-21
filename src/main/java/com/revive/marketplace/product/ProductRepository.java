package com.revive.marketplace.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    List<ProductModel> findByCategory(ProductCategory category);

    List<ProductModel> findByStatus(ProductStatus status);

    List<ProductModel> findByUserId(Long userId);
}
