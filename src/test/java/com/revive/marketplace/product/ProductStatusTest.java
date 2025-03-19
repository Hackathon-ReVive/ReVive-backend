package com.revive.marketplace.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductStatusTest {

    @Test
    @DisplayName("Check if all ProductStatus values exist")
    void test_Enum_ValuesExist() {
        assertNotNull(ProductStatus.AVAILABLE, "AVAILABLE should exist");
        assertNotNull(ProductStatus.SOLD, "SOLD should exist");
        assertNotNull(ProductStatus.RESERVED, "RESERVED should exist");
    }

    @Test
    @DisplayName("Ensure ProductStatus has exactly 3 values")
    void test_Enum_ValuesCount() {
        ProductStatus[] values = ProductStatus.values();
        assertEquals(3, values.length, "ProductStatus should have exactly 3 values");
    }

    @Test
    @DisplayName("Validate correct enum names")
    void test_EnumNames() {
        assertEquals("AVAILABLE", ProductStatus.AVAILABLE.name(), "AVAILABLE name should be correct");
        assertEquals("SOLD", ProductStatus.SOLD.name(), "SOLD name should be correct");
        assertEquals("RESERVED", ProductStatus.RESERVED.name(), "RESERVED name should be correct");
    }

    @Test
    @DisplayName("Verify ordinal values for each ProductStatus")
    void test_EnumOrdinal() {
        assertEquals(0, ProductStatus.AVAILABLE.ordinal(), "AVAILABLE should have ordinal 0");
        assertEquals(1, ProductStatus.SOLD.ordinal(), "SOLD should have ordinal 1");
        assertEquals(2, ProductStatus.RESERVED.ordinal(), "RESERVED should have ordinal 2");
    }

    @Test
    @DisplayName("Test valueOf() method returns the correct ProductStatus")
    void test_Value_Of_Method() {
        assertEquals(ProductStatus.AVAILABLE, ProductStatus.valueOf("AVAILABLE"), "valueOf should return AVAILABLE");
        assertEquals(ProductStatus.SOLD, ProductStatus.valueOf("SOLD"), "valueOf should return SOLD");
        assertEquals(ProductStatus.RESERVED, ProductStatus.valueOf("RESERVED"), "valueOf should return RESERVED");
    }

    @Test
    @DisplayName("Ensure valueOf() throws exception for invalid input")
    void test_Invalid_Value_Of_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> ProductStatus.valueOf("INVALID_STATUS"),
                "valueOf should throw IllegalArgumentException for invalid input");
    }
}
