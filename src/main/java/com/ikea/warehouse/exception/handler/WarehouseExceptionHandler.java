package com.ikea.warehouse.exception.handler;

import com.ikea.warehouse.exception.ProductNotFoundException;
import com.ikea.warehouse.exception.ProductOutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WarehouseExceptionHandler {

    /**
     * Product not found exception handler
     *
     * @param ex is the exception that is thrown
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ProductNotFoundException> productNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
    }

    /**
     * Product out of stock exception handler
     *
     * @param ex is the exception that is thrown
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ProductOutOfStockException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ProductOutOfStockException> productOutOfStockException(ProductOutOfStockException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
    }
}
