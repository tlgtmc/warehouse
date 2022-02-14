package com.ikea.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.domain.product.Product;
import com.ikea.warehouse.exception.ProductNotFoundException;
import com.ikea.warehouse.exception.handler.WarehouseExceptionHandler;
import com.ikea.warehouse.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static com.ikea.warehouse.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
@ContextConfiguration(classes = ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setMockMvc() {
        this.mockMvc = standaloneSetup(new ProductController(productService))
                .setControllerAdvice(new WarehouseExceptionHandler()).build();

    }

    @Test
    void test_getAllProducts_success() throws Exception {
        Map<String, Product> productsMap = getProductsMap();

        when(productService.getProducts()).thenReturn(productsMap);

        MvcResult mvcResult = mockMvc.perform(get(GET_PRODUCTS_ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        assertEquals(new ObjectMapper().writeValueAsString(productsMap), result);
        verify(productService).getProducts();
    }

    @Test
    void test_getProductByProductName_success() throws Exception {
        Product product = buildProductObject();

        when(productService.getProduct(product.getName())).thenReturn(product);

        MvcResult mvcResult = mockMvc.perform(get(GET_PRODUCT_BY_NAME_ENDPOINT, product.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        assertEquals(new ObjectMapper().writeValueAsString(product), result);
        verify(productService).getProduct(product.getName());
    }

    @Test
    void test_getProductByProductName_throws_productNotFoundException() throws Exception {
        Product product = buildProductObject();

        when(productService.getProduct(anyString())).thenThrow(ProductNotFoundException.class);

        mockMvc.perform(get(GET_PRODUCT_BY_NAME_ENDPOINT, product.getName()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException));
    }

    @Test
    void test_sellProduct_success() throws Exception {
        Product product = buildProductObject();

        mockMvc.perform(put(GET_PRODUCT_BY_NAME_ENDPOINT, product.getName()))
                .andDo(print())
                .andExpect(status().isOk());

        verify(productService).sellProduct(product.getName());
    }
}