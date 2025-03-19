package com.revive.marketplace.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class ProductRequestDTOTest {

    private ProductRequestDTO productRequestDTO;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        productRequestDTO = new ProductRequestDTO(
                "Smartphone",
                "Latest model smartphone",
                BigDecimal.valueOf(999.99),
                "smartphone.jpg",
                ProductCategory.ELECTRONICS,
                ProductStatus.AVAILABLE,
                true,
                1L);
    }

    @Test
    @DisplayName("Should create an empty ProductRequestDTO without errors")
    void should_Create_Empty_ProductRequestDTO() {
        ProductRequestDTO emptyDTO = new ProductRequestDTO();
        assertNotNull(emptyDTO, "Empty constructor should create a non-null object");
    }

    @Test
    @DisplayName("Should correctly initialize ProductRequestDTO with parameters")
    void should_Create_ProductRequestDTO_With_Valid_Parameters() {
        assertNotNull(productRequestDTO);
        assertEquals("Smartphone", productRequestDTO.getTitle());
        assertEquals("Latest model smartphone", productRequestDTO.getDescription());
        assertEquals(BigDecimal.valueOf(999.99), productRequestDTO.getPrice());
        assertEquals("smartphone.jpg", productRequestDTO.getImage());
        assertEquals(ProductCategory.ELECTRONICS, productRequestDTO.getCategory());
        assertEquals(ProductStatus.AVAILABLE, productRequestDTO.getStatus());
        assertTrue(productRequestDTO.isLiked());
        assertEquals(1L, productRequestDTO.getUserId());
    }

    @Test
    @DisplayName("Should correctly return values from getters")
    void should_Return_Correct_Values_From_Getters() {
        assertEquals("Smartphone", productRequestDTO.getTitle());
        assertEquals("Latest model smartphone", productRequestDTO.getDescription());
        assertEquals(BigDecimal.valueOf(999.99), productRequestDTO.getPrice());
        assertEquals("smartphone.jpg", productRequestDTO.getImage());
        assertEquals(ProductCategory.ELECTRONICS, productRequestDTO.getCategory());
        assertEquals(ProductStatus.AVAILABLE, productRequestDTO.getStatus());
        assertTrue(productRequestDTO.isLiked());
        assertEquals(1L, productRequestDTO.getUserId());
    }

    @Test
    @DisplayName("Should correctly update values using setters")
    void should_Update_Values_Using_Setters() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setTitle("Tablet");
        dto.setDescription("10-inch screen tablet");
        dto.setPrice(BigDecimal.valueOf(499.99));
        dto.setImage("tablet.jpg");
        dto.setLiked(false);
        dto.setUserId(5L);

        assertEquals("Tablet", dto.getTitle());
        assertEquals("10-inch screen tablet", dto.getDescription());
        assertEquals(BigDecimal.valueOf(499.99), dto.getPrice());
        assertEquals("tablet.jpg", dto.getImage());
        assertFalse(dto.isLiked());
        assertEquals(5L, dto.getUserId());
    }

    @Test
    @DisplayName("Should fail validation when required fields are missing")
    void should_Fail_Validation_When_Fields_Are_Missing() {
        ProductRequestDTO invalidDTO = new ProductRequestDTO();
        Set<ConstraintViolation<ProductRequestDTO>> violations = validator.validate(invalidDTO);
        assertFalse(violations.isEmpty(), "Validation should fail for missing required fields");

        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Title is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Description is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Price is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Image URL is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Category is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Status is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("User ID is required")));
    }

    @Test
    @DisplayName("Should fail validation when price is zero or negative")
    void should_Fail_Validation_When_Price_Is_NonPositive() {
        ProductRequestDTO invalidDTO = new ProductRequestDTO(
                "Phone", "Description", BigDecimal.valueOf(0),
                "image.jpg", ProductCategory.ELECTRONICS, ProductStatus.AVAILABLE, false, 1L);

        Set<ConstraintViolation<ProductRequestDTO>> violations = validator.validate(invalidDTO);
        assertFalse(violations.isEmpty(), "Validation should fail for non-positive price");

        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Price must be greater than zero")));
    }
}
