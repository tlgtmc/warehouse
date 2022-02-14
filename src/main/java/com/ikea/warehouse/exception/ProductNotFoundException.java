package com.ikea.warehouse.exception;

public class ProductNotFoundException extends RuntimeException {

    /**
     * Product not found exception
     *
     * @param productName
     */
    public ProductNotFoundException(String productName) {
        super(String.format("Product '%s' not found in the warehouse", productName));
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
