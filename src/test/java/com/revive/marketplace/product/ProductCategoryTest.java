package com.revive.marketplace.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryTest {

    @Test
    @DisplayName("Check if all ProductCategory values exist")
    void test_Enum_ValuesExist() {
        assertNotNull(ProductCategory.ELECTRONICS, "ELECTRONICS should exist");
        assertNotNull(ProductCategory.FASHION, "FASHION should exist");
        assertNotNull(ProductCategory.HOME, "HOME should exist");
        assertNotNull(ProductCategory.SPORTS, "SPORTS should exist");
        assertNotNull(ProductCategory.AUTOMOTIVE, "AUTOMOTIVE should exist");
        assertNotNull(ProductCategory.BOOKS, "BOOKS should exist");
        assertNotNull(ProductCategory.TOYS, "TOYS should exist");
        assertNotNull(ProductCategory.HEALTH, "HEALTH should exist");
        assertNotNull(ProductCategory.BEAUTY, "BEAUTY should exist");
        assertNotNull(ProductCategory.GARDEN, "GARDEN should exist");
        assertNotNull(ProductCategory.OTHER, "OTHER should exist");
    }

    @Test
    @DisplayName("Ensure ProductCategory has exactly 11 values")
    void test_Enum_ValuesCount() {
        ProductCategory[] values = ProductCategory.values();
        assertEquals(11, values.length, "ProductCategory should have exactly 11 values");
    }

    @Test
    @DisplayName("Validate correct enum names")
    void test_Enum_Names() {
        assertEquals("ELECTRONICS", ProductCategory.ELECTRONICS.name(), "ELECTRONICS name should be correct");
        assertEquals("FASHION", ProductCategory.FASHION.name(), "FASHION name should be correct");
        assertEquals("HOME", ProductCategory.HOME.name(), "HOME name should be correct");
        assertEquals("SPORTS", ProductCategory.SPORTS.name(), "SPORTS name should be correct");
        assertEquals("AUTOMOTIVE", ProductCategory.AUTOMOTIVE.name(), "AUTOMOTIVE name should be correct");
        assertEquals("BOOKS", ProductCategory.BOOKS.name(), "BOOKS name should be correct");
        assertEquals("TOYS", ProductCategory.TOYS.name(), "TOYS name should be correct");
        assertEquals("HEALTH", ProductCategory.HEALTH.name(), "HEALTH name should be correct");
        assertEquals("BEAUTY", ProductCategory.BEAUTY.name(), "BEAUTY name should be correct");
        assertEquals("GARDEN", ProductCategory.GARDEN.name(), "GARDEN name should be correct");
        assertEquals("OTHER", ProductCategory.OTHER.name(), "OTHER name should be correct");
    }

    @Test
    @DisplayName("Verify ordinal values for each ProductCategory")
    void test_Enum_Ordinal() {
        assertEquals(0, ProductCategory.ELECTRONICS.ordinal(), "ELECTRONICS should have ordinal 0");
        assertEquals(1, ProductCategory.FASHION.ordinal(), "FASHION should have ordinal 1");
        assertEquals(2, ProductCategory.HOME.ordinal(), "HOME should have ordinal 2");
        assertEquals(3, ProductCategory.SPORTS.ordinal(), "SPORTS should have ordinal 3");
        assertEquals(4, ProductCategory.AUTOMOTIVE.ordinal(), "AUTOMOTIVE should have ordinal 4");
        assertEquals(5, ProductCategory.BOOKS.ordinal(), "BOOKS should have ordinal 5");
        assertEquals(6, ProductCategory.TOYS.ordinal(), "TOYS should have ordinal 6");
        assertEquals(7, ProductCategory.HEALTH.ordinal(), "HEALTH should have ordinal 7");
        assertEquals(8, ProductCategory.BEAUTY.ordinal(), "BEAUTY should have ordinal 8");
        assertEquals(9, ProductCategory.GARDEN.ordinal(), "GARDEN should have ordinal 9");
        assertEquals(10, ProductCategory.OTHER.ordinal(), "OTHER should have ordinal 10");
    }

    @Test
    @DisplayName("Test valueOf() method returns the correct ProductCategory")
    void test_Value_Of_Method() {
        assertEquals(ProductCategory.ELECTRONICS, ProductCategory.valueOf("ELECTRONICS"),
                "valueOf should return ELECTRONICS");
        assertEquals(ProductCategory.FASHION, ProductCategory.valueOf("FASHION"), "valueOf should return FASHION");
        assertEquals(ProductCategory.HOME, ProductCategory.valueOf("HOME"), "valueOf should return HOME");
        assertEquals(ProductCategory.SPORTS, ProductCategory.valueOf("SPORTS"), "valueOf should return SPORTS");
        assertEquals(ProductCategory.AUTOMOTIVE, ProductCategory.valueOf("AUTOMOTIVE"),
                "valueOf should return AUTOMOTIVE");
        assertEquals(ProductCategory.BOOKS, ProductCategory.valueOf("BOOKS"), "valueOf should return BOOKS");
        assertEquals(ProductCategory.TOYS, ProductCategory.valueOf("TOYS"), "valueOf should return TOYS");
        assertEquals(ProductCategory.HEALTH, ProductCategory.valueOf("HEALTH"), "valueOf should return HEALTH");
        assertEquals(ProductCategory.BEAUTY, ProductCategory.valueOf("BEAUTY"), "valueOf should return BEAUTY");
        assertEquals(ProductCategory.GARDEN, ProductCategory.valueOf("GARDEN"), "valueOf should return GARDEN");
        assertEquals(ProductCategory.OTHER, ProductCategory.valueOf("OTHER"), "valueOf should return OTHER");
    }

    @Test
    @DisplayName("Ensure valueOf() throws exception for invalid input")
    void test_Invalid_Value_Of_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> ProductCategory.valueOf("INVALID_CATEGORY"),
                "valueOf should throw IllegalArgumentException for invalid input");
    }
}

