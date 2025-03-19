package com.revive.marketplace.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    @DisplayName("Debe contener los valores esperados")
    void should_Contain_Expected_Values() {
        assertEquals(3, OrderStatus.values().length, "El enum OrderStatus debe contener exactamente 3 valores");

        assertNotNull(OrderStatus.valueOf("PENDING"), "Debe contener el estado PENDING");
        assertNotNull(OrderStatus.valueOf("CONFIRMED"), "Debe contener el estado CONFIRMED");
        assertNotNull(OrderStatus.valueOf("CANCELED"), "Debe contener el estado CANCELED");
    }

    @Test
    @DisplayName("Debe permitir conversión desde String")
    void should_Convert_String_To_Enum() {
        assertEquals(OrderStatus.PENDING, OrderStatus.valueOf("PENDING"), "La conversión de 'PENDING' debe ser correcta");
        assertEquals(OrderStatus.CONFIRMED, OrderStatus.valueOf("CONFIRMED"), "La conversión de 'CONFIRMED' debe ser correcta");
        assertEquals(OrderStatus.CANCELED, OrderStatus.valueOf("CANCELED"), "La conversión de 'CANCELED' debe ser correcta");
    }

    @Test
    @DisplayName("Debe lanzar excepción si el valor no existe")
    void should_Throw_Exception_For_Invalid_Value() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> OrderStatus.valueOf("INVALID"));

        assertTrue(exception.getMessage().contains("INVALID"), "Debe lanzar IllegalArgumentException si el valor no existe");
    }
}

