package com.revive.marketplace.product;

import com.revive.marketplace.user.User;
import com.revive.marketplace.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductService productService;

    private User mockUser;
    private ProductModel mockProduct;
    private ProductRequestDTO mockRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");

        mockProduct = new ProductModel(
                "Smartphone",
                "High-end smartphone",
                BigDecimal.valueOf(999.99),
                "smartphone.jpg",
                ProductCategory.ELECTRONICS,
                ProductStatus.AVAILABLE,
                true,
                mockUser
        );

        mockRequestDTO = new ProductRequestDTO(
                "Smartphone",
                "High-end smartphone",
                BigDecimal.valueOf(999.99),
                "smartphone.jpg",
                ProductCategory.ELECTRONICS,
                ProductStatus.AVAILABLE,
                true,
                1L
        );
    }

    @Test
    @DisplayName("Should create a new product successfully")
    void should_Create_Product_Successfully() {
        when(userRepository.findById(mockRequestDTO.getUserId())).thenReturn(Optional.of(mockUser));
        when(productRepository.save(any(ProductModel.class))).thenReturn(mockProduct);

        ProductDTO createdProduct = productService.createProduct(mockRequestDTO);

        assertNotNull(createdProduct);
        assertEquals(mockRequestDTO.getTitle(), createdProduct.getTitle());
        assertEquals(mockRequestDTO.getDescription(), createdProduct.getDescription());
        verify(productRepository, times(1)).save(any(ProductModel.class));
    }

    @Test
    @DisplayName("Should throw exception when creating a product with null user ID")
    void should_Throw_Exception_When_UserId_Is_Null() {
        mockRequestDTO.setUserId(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productService.createProduct(mockRequestDTO));

        assertEquals("El usuario no puede ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when user is not found")
    void should_Throw_Exception_When_User_Not_Found() {
        when(userRepository.findById(mockRequestDTO.getUserId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productService.createProduct(mockRequestDTO));

        assertEquals("Usuario no encontrado en la BD", exception.getMessage());
    }

    @Test
    @DisplayName("Should retrieve a product by ID successfully")
    void should_Get_Product_By_Id() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        ProductDTO foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals(mockProduct.getTitle(), foundProduct.getTitle());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when product not found by ID")
    void should_Throw_Exception_When_Product_Not_Found() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productService.getProductById(1L));

        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Should retrieve all products successfully")
    void should_Get_All_Products_Successfully() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(mockProduct));

        List<ProductDTO> products = productService.getAllProducts();

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should retrieve products by category")
    void should_Get_Products_By_Category() {
        when(productRepository.findByCategory(ProductCategory.ELECTRONICS)).thenReturn(Arrays.asList(mockProduct));

        List<ProductDTO> products = productService.getProductsByCategory(ProductCategory.ELECTRONICS);

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findByCategory(ProductCategory.ELECTRONICS);
    }

    @Test
    @DisplayName("Should retrieve products by status")
    void should_Get_Products_By_Status() {
        when(productRepository.findByStatus(ProductStatus.AVAILABLE)).thenReturn(Arrays.asList(mockProduct));

        List<ProductDTO> products = productService.getProductsByStatus(ProductStatus.AVAILABLE);

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findByStatus(ProductStatus.AVAILABLE);
    }

    @Test
    @DisplayName("Should update a product successfully")
    void should_Update_Product_Successfully() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        when(productRepository.save(any(ProductModel.class))).thenReturn(mockProduct);

        ProductRequestDTO updatedRequest = new ProductRequestDTO(
                "Updated Smartphone",
                "Updated high-end smartphone",
                BigDecimal.valueOf(1099.99),
                "updated-smartphone.jpg",
                ProductCategory.ELECTRONICS,
                ProductStatus.SOLD,
                false,
                1L
        );

        ProductDTO updatedProduct = productService.updateProduct(1L, updatedRequest, "test@example.com");

        assertNotNull(updatedProduct);
        assertEquals("Updated Smartphone", updatedProduct.getTitle());
        assertEquals(BigDecimal.valueOf(1099.99), updatedProduct.getPrice());
        verify(productRepository, times(1)).save(any(ProductModel.class));
    }

    @Test
    @DisplayName("Should throw exception when updating a product without permission")
    void should_Throw_Exception_When_Updating_Product_Without_Permission() {
        mockProduct.getUser().setEmail("different@example.com");

        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        SecurityException exception = assertThrows(SecurityException.class,
                () -> productService.updateProduct(1L, mockRequestDTO, "test@example.com"));

        assertEquals("No tienes permiso para modificar este producto", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete a product successfully")
    void should_Delete_Product_Successfully() {
        doNothing().when(productRepository).deleteById(1L);

        assertDoesNotThrow(() -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should handle exception when deleting non-existing product")
    void should_Handle_Exception_When_Deleting_NonExisting_Product() {
        doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(99L);

        assertThrows(EmptyResultDataAccessException.class, () -> productService.deleteProduct(99L));
    }
}

