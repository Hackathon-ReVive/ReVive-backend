package com.revive.marketplace.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.revive.marketplace.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class ProductDTOTest {

    private ProductDTO productDTO;
    private ProductModel productModel;
    private LocalDateTime createdAt;
    private Long userId;

    @BeforeEach
    void setUp() {
        userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);

        createdAt = LocalDateTime.now();

        productModel = new ProductModel(
                "Laptop",
                "High-performance laptop",
                BigDecimal.valueOf(1200.99),
                "laptop.jpg",
                ProductCategory.ELECTRONICS,
                ProductStatus.AVAILABLE,
                false,
                mockUser
        );

        productDTO = new ProductDTO(
                1L,
                "Smartphone",
                "Latest model smartphone",
                BigDecimal.valueOf(999.99),
                "smartphone.jpg",
                ProductCategory.ELECTRONICS,
                ProductStatus.SOLD,
                true,
                createdAt,
                userId
        );
    }

    @Test
    @DisplayName("Should create an empty ProductDTO without errors")
    void should_Create_Empty_ProductDTO() {
        ProductDTO emptyDTO = new ProductDTO();
        assertNotNull(emptyDTO, "Empty constructor should create a non-null object");
    }

    @Test
    @DisplayName("Should correctly initialize ProductDTO with parameterized constructor")
    void should_Create_ProductDTO_With_Valid_Parameters() {
        assertNotNull(productDTO);
        assertEquals(1L, productDTO.getId());
        assertEquals("Smartphone", productDTO.getTitle());
        assertEquals("Latest model smartphone", productDTO.getDescription());
        assertEquals(BigDecimal.valueOf(999.99), productDTO.getPrice());
        assertEquals("smartphone.jpg", productDTO.getImage());
        assertEquals(ProductCategory.ELECTRONICS, productDTO.getCategory());
        assertEquals(ProductStatus.SOLD, productDTO.getStatus());
        assertTrue(productDTO.isLiked());
        assertEquals(createdAt, productDTO.getCreatedAt());
        assertEquals(userId, productDTO.getUserId());
    }

    @Test
    @DisplayName("Should correctly convert ProductModel to ProductDTO")
    void should_Convert_ProductModel_To_ProductDTO() {
        ProductDTO convertedDTO = new ProductDTO(productModel);
        assertNotNull(convertedDTO);
        assertEquals(productModel.getTitle(), convertedDTO.getTitle());
        assertEquals(productModel.getDescription(), convertedDTO.getDescription());
        assertEquals(productModel.getPrice(), convertedDTO.getPrice());
        assertEquals(productModel.getImage(), convertedDTO.getImage());
        assertEquals(productModel.getCategory(), convertedDTO.getCategory());
        assertEquals(productModel.getStatus(), convertedDTO.getStatus());
        assertEquals(productModel.isLiked(), convertedDTO.isLiked());
        assertEquals(productModel.getCreatedAt(), convertedDTO.getCreatedAt());
        assertEquals(productModel.getUser().getId(), convertedDTO.getUserId());
    }

    @Test
    @DisplayName("Should correctly return values from getters")
    void should_Return_Correct_Values_From_Getters() {
        assertEquals(1L, productDTO.getId());
        assertEquals("Smartphone", productDTO.getTitle());
        assertEquals("Latest model smartphone", productDTO.getDescription());
        assertEquals(BigDecimal.valueOf(999.99), productDTO.getPrice());
        assertEquals("smartphone.jpg", productDTO.getImage());
        assertEquals(ProductCategory.ELECTRONICS, productDTO.getCategory());
        assertEquals(ProductStatus.SOLD, productDTO.getStatus());
        assertTrue(productDTO.isLiked());
        assertEquals(createdAt, productDTO.getCreatedAt());
        assertEquals(userId, productDTO.getUserId());
    }

    @Test
    @DisplayName("Should correctly update values using setters")
    void should_Update_Values_Using_Setters() {
        ProductDTO dto = new ProductDTO();
        LocalDateTime updatedTime = LocalDateTime.now();

        dto.setId(2L);
        dto.setTitle("Tablet");
        dto.setDescription("10-inch screen tablet");
        dto.setPrice(BigDecimal.valueOf(499.99));
        dto.setImage("tablet.jpg");
        dto.setCategory(ProductCategory.ELECTRONICS);
        dto.setStatus(ProductStatus.RESERVED);
        dto.setLiked(false);
        dto.setCreatedAt(updatedTime);
        dto.setUserId(5L);

        assertEquals(2L, dto.getId());
        assertEquals("Tablet", dto.getTitle());
        assertEquals("10-inch screen tablet", dto.getDescription());
        assertEquals(BigDecimal.valueOf(499.99), dto.getPrice());
        assertEquals("tablet.jpg", dto.getImage());
        assertEquals(ProductCategory.ELECTRONICS, dto.getCategory());
        assertEquals(ProductStatus.RESERVED, dto.getStatus());
        assertFalse(dto.isLiked());
        assertEquals(updatedTime, dto.getCreatedAt());
        assertEquals(5L, dto.getUserId());
    }
}

