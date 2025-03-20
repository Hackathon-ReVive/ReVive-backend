package com.revive.marketplace.product;


import com.revive.marketplace.user.User;
import com.revive.marketplace.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductService productService;

    private User user;
    private ProductModel product;
    private ProductRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        product = new ProductModel(
                "Laptop",
                "Gaming laptop",
                BigDecimal.valueOf(1500.00),
                "laptop.jpg",
                ProductCategory.ELECTRONICS,
                ProductStatus.AVAILABLE,
                false,
                user
        );

        requestDTO = new ProductRequestDTO();
        requestDTO.setTitle("Laptop");
        requestDTO.setDescription("Gaming laptop");
        requestDTO.setPrice(BigDecimal.valueOf(1500.00));
        requestDTO.setImage("laptop.jpg");
        requestDTO.setLiked(false);
        requestDTO.setUserId(user.getId());
    }

    @Test
    @DisplayName("Should successfully create a product")
    void should_Create_Product_Successfully() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(productRepository.save(any(ProductModel.class))).thenReturn(product);

        ProductDTO result = productService.createProduct(requestDTO);

        assertNotNull(result);
        assertEquals(product.getTitle(), result.getTitle());
        verify(productRepository).save(any(ProductModel.class));
    }

    @Test
    @DisplayName("Should throw exception when user not found during product creation")
    void should_Throw_Exception_When_User_Not_Found_On_Create() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.createProduct(requestDTO));
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should get product by ID successfully")
    void should_Get_Product_By_Id_Successfully() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        ProductDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(product.getTitle(), result.getTitle());
    }

    @Test
    @DisplayName("Should throw exception when product ID not found")
    void should_Throw_Exception_When_Product_Not_Found_By_Id() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProductById(99L));
    }

    @Test
    @DisplayName("Should update product successfully")
    void should_Update_Product_Successfully() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(ProductModel.class))).thenReturn(product);

        ProductDTO result = productService.updateProduct(1L, requestDTO, "user@example.com");

        assertNotNull(result);
        assertEquals(requestDTO.getTitle(), result.getTitle());
    }

    @Test
    @DisplayName("Should throw SecurityException if unauthorized update attempt")
    void should_Throw_SecurityException_When_Unauthorized_Update() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        assertThrows(SecurityException.class, () -> productService.updateProduct(1L, requestDTO, "another@example.com"));
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete product by ID successfully")
    void should_Delete_Product_Successfully() {
        doNothing().when(productRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> productService.deleteProduct(1L));
        verify(productRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should get all products successfully")
    void should_Get_All_Products_Successfully() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductDTO> results = productService.getAllProducts();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(product.getTitle(), results.get(0).getTitle());
    }
}

