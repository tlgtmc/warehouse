package com.ikea.warehouse.exception;

import com.ikea.warehouse.exception.handler.WarehouseExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.ikea.warehouse.util.TestUtil.PRODUCT_NAME;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class WarehouseExceptionHandlerTest {

    @InjectMocks
    private WarehouseExceptionHandler warehouseExceptionHandler;

    @Test
    void test_productOutOfStockException() {
        ResponseEntity<ProductOutOfStockException> productOutOfStockException = warehouseExceptionHandler.productOutOfStockException(new ProductOutOfStockException(PRODUCT_NAME));
        assertThat(productOutOfStockException).isNotNull();
        assertThat(productOutOfStockException.getBody().getMessage()).isEqualTo(String.format("Product '%s' is currently out of stock", PRODUCT_NAME));
        assertThat(productOutOfStockException.getStatusCodeValue()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void test_productNotFoundException() {
        ResponseEntity<ProductNotFoundException> productNotFoundException = warehouseExceptionHandler.productNotFoundException(new ProductNotFoundException(PRODUCT_NAME));
        assertThat(productNotFoundException).isNotNull();
        assertThat(productNotFoundException.getBody().getMessage()).isEqualTo(String.format("Product '%s' not found in the warehouse", PRODUCT_NAME));
        assertThat(productNotFoundException.getStatusCodeValue()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
