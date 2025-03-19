package com.revive.marketplace.product;

import com.revive.marketplace.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductModelTest {

    private ProductModel product;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        product = new ProductModel(
                "Smartphone",
                "High-end smartphone",
                BigDecimal.valueOf(999.99),
                "smartphone.jpg",
                ProductCategory.ELECTRONICS,
                ProductStatus.AVAILABLE,
                true,
                user);
    }

    @Test
    @DisplayName("Should create ProductModel instance with correct values")
    void should_Create_ProductModel_Correctly() {
        assertNotNull(product);
        assertEquals("Smartphone", product.getTitle());
        assertEquals("High-end smartphone", product.getDescription());
        assertEquals(BigDecimal.valueOf(999.99), product.getPrice());
        assertEquals("smartphone.jpg", product.getImage());
        assertEquals(ProductCategory.ELECTRONICS, product.getCategory());
        assertEquals(ProductStatus.AVAILABLE, product.getStatus());
        assertTrue(product.isLiked());
        assertNotNull(product.getCreatedAt());
        assertEquals(user, product.getUser());
    }

    @Test
    @DisplayName("Should set and get title correctly")
    void should_Set_And_Get_Title_Correctly() {
        product.setTitle("Laptop");
        assertEquals("Laptop", product.getTitle());
    }

    @Test
    @DisplayName("Should set and get description correctly")
    void should_Set_And_Get_Description_Correctly() {
        product.setDescription("Gaming Laptop");
        assertEquals("Gaming Laptop", product.getDescription());
    }

    @Test
    @DisplayName("Should set and get price correctly")
    void should_Set_And_Get_Price_Correctly() {
        BigDecimal newPrice = BigDecimal.valueOf(1499.99);
        product.setPrice(newPrice);
        assertEquals(newPrice, product.getPrice());
    }

    @Test
    @DisplayName("Should set and get image correctly")
    void should_Set_And_Get_Image_Correctly() {
        product.setImage("laptop.jpg");
        assertEquals("laptop.jpg", product.getImage());
    }

    @Test
    @DisplayName("Should set and get category correctly")
    void should_Set_And_Get_Category_Correctly() {
        product.setCategory(ProductCategory.FASHION);
        assertEquals(ProductCategory.FASHION, product.getCategory());
    }

    @Test
    @DisplayName("Should set and get status correctly")
    void should_Set_And_Get_Status_Correctly() {
        product.setStatus(ProductStatus.SOLD);
        assertEquals(ProductStatus.SOLD, product.getStatus());
    }

    @Test
    @DisplayName("Should set and get liked status correctly")
    void should_Set_And_Get_Liked_Correctly() {
        product.setLiked(false);
        assertFalse(product.isLiked());
    }

    @Test
    @DisplayName("Should set and get user correctly")
    void should_Set_And_Get_User_Correctly() {
        User newUser = new User();
        newUser.setId(2L);
        product.setUser(newUser);
        assertEquals(newUser, product.getUser());
    }

    @Test
    @DisplayName("Should return correct toString output")
    void should_Return_Correct_ToString() {
        String expectedString = "ProductModel{" +
                "id=null, " +
                "title='Smartphone', " +
                "description='High-end smartphone', " +
                "price=999.99, " +
                "image='smartphone.jpg', " +
                "category=ELECTRONICS, " +
                "status=AVAILABLE, " +
                "liked=true, " +
                "createdAt=" + product.getCreatedAt() + ", " +
                "user=1" +
                "}";

        assertEquals(expectedString, product.toString());
    }
}
