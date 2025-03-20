package com.revive.marketplace.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;
    private ProductDTO mockProduct;
    private ProductRequestDTO mockRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        mockProduct = new ProductDTO(
                1L,
                "Smartphone",
                "High-end smartphone",
                BigDecimal.valueOf(999.99),
                "smartphone.jpg",
                ProductCategory.ELECTRONICS,
                ProductStatus.AVAILABLE,
                true,
                null,
                1L);

        mockRequestDTO = new ProductRequestDTO(
                "Smartphone",
                "High-end smartphone",
                BigDecimal.valueOf(999.99),
                "smartphone.jpg",
                ProductCategory.ELECTRONICS,
                ProductStatus.AVAILABLE,
                true,
                1L);
    }

    @Test
    @DisplayName("Should create a new product successfully")
    void should_Create_Product_Successfully() throws Exception {
        when(productService.createProduct(any(ProductRequestDTO.class))).thenReturn(mockProduct);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(mockProduct.getTitle()))
                .andExpect(jsonPath("$.price").value(mockProduct.getPrice().doubleValue()));

        verify(productService, times(1)).createProduct(any(ProductRequestDTO.class));
    }

    @Test
    @DisplayName("Should retrieve a product by ID successfully")
    void should_Get_Product_By_Id() throws Exception {
        when(productService.getProductById(1L)).thenReturn(mockProduct);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(mockProduct.getTitle()));
    }

    @Test
    @DisplayName("Should retrieve all products successfully")
    void should_Get_All_Products_Successfully() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(mockProduct));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Should retrieve products by category")
    void should_Get_Products_By_Category() throws Exception {
        when(productService.getProductsByCategory(ProductCategory.ELECTRONICS))
                .thenReturn(List.of(mockProduct));

        mockMvc.perform(get("/api/products/category/ELECTRONICS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Should retrieve products by status")
    void should_Get_Products_By_Status() throws Exception {
        when(productService.getProductsByStatus(ProductStatus.AVAILABLE)).thenReturn(List.of(mockProduct));

        mockMvc.perform(get("/api/products/status/AVAILABLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should delete a product successfully")
    void should_Delete_Product_Successfully() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);
    }
}