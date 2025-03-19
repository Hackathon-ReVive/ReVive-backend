package com.revive.marketplace.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
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
        private UserDetails mockUserDetails;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

                mockUserDetails = org.springframework.security.core.userdetails.User.builder()
                                .username("test@example.com")
                                .password("password")
                                .roles("USER")
                                .build();

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
                                .content("""
                                                {
                                                  "title": "Smartphone",
                                                  "description": "High-end smartphone",
                                                  "price": 999.99,
                                                  "image": "smartphone.jpg",
                                                  "category": "ELECTRONICS",
                                                  "status": "AVAILABLE",
                                                  "liked": true,
                                                  "userId": 1
                                                }
                                                """))
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

                verify(productService, times(1)).getProductById(1L);
        }

        @Test
        @DisplayName("Should retrieve all products successfully")
        void should_Get_All_Products_Successfully() throws Exception {
                when(productService.getAllProducts()).thenReturn(List.of(mockProduct));

                mockMvc.perform(get("/api/products"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));

                verify(productService, times(1)).getAllProducts();
        }

        @Test
        @DisplayName("Should retrieve products by category")
        void should_Get_Products_By_Category() throws Exception {
                when(productService.getProductsByCategory(ProductCategory.ELECTRONICS))
                                .thenReturn(List.of(mockProduct));

                mockMvc.perform(get("/api/products/category/ELECTRONICS"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));

                verify(productService, times(1)).getProductsByCategory(ProductCategory.ELECTRONICS);
        }

        @Test
        @DisplayName("Should retrieve products by status")
        void should_Get_Products_By_Status() throws Exception {
                when(productService.getProductsByStatus(ProductStatus.AVAILABLE)).thenReturn(List.of(mockProduct));

                mockMvc.perform(get("/api/products/status/AVAILABLE"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));

                verify(productService, times(1)).getProductsByStatus(ProductStatus.AVAILABLE);
        }
        /*
         * @Test
         * 
         * @DisplayName("Should update product successfully with authentication")
         * void should_Update_Product_Successfully() throws Exception {
         * 
         * UserDetails mockUserDetails = new
         * org.springframework.security.core.userdetails.User(
         * "test@example.com",
         * "password",
         * Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
         * );
         * 
         * ProductDTO mockProduct = new ProductDTO(
         * 1L, "Updated Smartphone", "Updated high-end smartphone",
         * BigDecimal.valueOf(1099.99), "updated-smartphone.jpg",
         * ProductCategory.ELECTRONICS, ProductStatus.SOLD, false,
         * LocalDateTime.now(), 1L
         * );
         * 
         * when(productService.updateProduct(eq(1L), any(ProductRequestDTO.class),
         * eq("test@example.com")))
         * .thenReturn(mockProduct);
         * 
         * String updatedProductJson = """
         * {
         * "title": "Updated Smartphone",
         * "description": "Updated high-end smartphone",
         * "price": 1099.99,
         * "image": "updated-smartphone.jpg",
         * "category": "ELECTRONICS",
         * "status": "SOLD",
         * "liked": false,
         * "userId": 1
         * }
         * """;
         * 
         * mockMvc.perform(put("/api/products/1")
         * .contentType(MediaType.APPLICATION_JSON)
         * .content(updatedProductJson)
         * .with(SecurityMockMvcRequestPostProcessors.user(mockUserDetails))
         * )
         * .andExpect(status().isOk())
         * .andExpect(jsonPath("$.title").value(mockProduct.getTitle()))
         * .andExpect(jsonPath("$.price").value(1099.99));
         * 
         * verify(productService, times(1)).updateProduct(eq(1L),
         * any(ProductRequestDTO.class), eq("test@example.com"));
         * }
         */

        @Test
        @DisplayName("Should delete a product successfully")
        void should_Delete_Product_Successfully() throws Exception {
                doNothing().when(productService).deleteProduct(1L);

                mockMvc.perform(delete("/api/products/1"))
                                .andExpect(status().isNoContent());

                verify(productService, times(1)).deleteProduct(1L);
        }
}

