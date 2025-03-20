package com.revive.marketplace.user;

import com.revive.marketplace.order.Order;
import com.revive.marketplace.product.ProductModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("JohnDoe");
        user.setPassword("SecurePass123");
        user.setEmail("johndoe@example.com");
        user.setPhonenumber("+1234567890");
        user.setAddress("123 Main Street, City");
        user.setRole(User.UserRole.USER);
        user.setProducts(new ArrayList<>());
        user.setOrders(new ArrayList<>());
    }

    @Test
    @DisplayName("Should create User object using default constructor")
    void should_Create_User_With_Default_Constructor() {
        User newUser = new User();
        assertNotNull(newUser, "User object should not be null");
    }

    @Test
    @DisplayName("Should correctly return values from getters")
    void should_Return_Correct_Values_From_Getters() {
        assertEquals(1L, user.getId());
        assertEquals("JohnDoe", user.getUsername());
        assertEquals("SecurePass123", user.getPassword());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("+1234567890", user.getPhonenumber());
        assertEquals("123 Main Street, City", user.getAddress());
        assertEquals(User.UserRole.USER, user.getRole());
        assertTrue(user.getProducts().isEmpty());
        assertTrue(user.getOrders().isEmpty());
    }

    @Test
    @DisplayName("Should correctly update values using setters")
    void should_Update_Values_Using_Setters() {
        user.setId(2L);
        user.setUsername("JaneDoe");
        user.setPassword("NewSecurePass456");
        user.setEmail("janedoe@example.com");
        user.setPhonenumber("+9876543210");
        user.setAddress("456 Another Street, City");
        user.setRole(User.UserRole.ADMIN);

        assertEquals(2L, user.getId());
        assertEquals("JaneDoe", user.getUsername());
        assertEquals("NewSecurePass456", user.getPassword());
        assertEquals("janedoe@example.com", user.getEmail());
        assertEquals("+9876543210", user.getPhonenumber());
        assertEquals("456 Another Street, City", user.getAddress());
        assertEquals(User.UserRole.ADMIN, user.getRole());
    }

    @Test
    @DisplayName("Should correctly manage product association")
    void should_Correctly_Manage_Product_Association() {
        ProductModel product1 = new ProductModel();
        ProductModel product2 = new ProductModel();

        List<ProductModel> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        user.setProducts(products);

        assertEquals(2, user.getProducts().size());
        assertTrue(user.getProducts().contains(product1));
        assertTrue(user.getProducts().contains(product2));
    }

    @Test
    @DisplayName("Should correctly manage order association")
    void should_Correctly_Manage_Order_Association() {
        Order order1 = new Order();
        Order order2 = new Order();

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        user.setOrders(orders);

        assertEquals(2, user.getOrders().size());
        assertTrue(user.getOrders().contains(order1));
        assertTrue(user.getOrders().contains(order2));
    }

    @Test
    @DisplayName("Should allow setting null values in optional fields")
    void should_Allow_Null_Values_In_Optional_Fields() {
        user.setPhonenumber(null);
        user.setAddress(null);

        assertNull(user.getPhonenumber(), "Phone number should be null");
        assertNull(user.getAddress(), "Address should be null");
    }

    @Test
    @DisplayName("Should allow setting empty lists for products and orders")
    void should_Allow_Empty_Lists_For_Products_And_Orders() {
        user.setProducts(new ArrayList<>());
        user.setOrders(new ArrayList<>());

        assertNotNull(user.getProducts(), "Products list should not be null");
        assertNotNull(user.getOrders(), "Orders list should not be null");
        assertTrue(user.getProducts().isEmpty(), "Products list should be empty");
        assertTrue(user.getOrders().isEmpty(), "Orders list should be empty");
    }

    @Test
    @DisplayName("Should correctly handle user role enumeration")
    void should_Handle_User_Role_Enumeration() {
        user.setRole(User.UserRole.ADMIN);
        assertEquals(User.UserRole.ADMIN, user.getRole());

        user.setRole(User.UserRole.USER);
        assertEquals(User.UserRole.USER, user.getRole());
    }

    @Test
    @DisplayName("Should return correct role names")
    void should_Return_Correct_Role_Names() {
        assertEquals("USER", User.UserRole.USER.name());
        assertEquals("ADMIN", User.UserRole.ADMIN.name());
    }
}
