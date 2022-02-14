package com.ikea.warehouse.exception;

public class ProductOutOfStockException extends RuntimeException {

    /**
     * Product out of stock exception
     *
     * @param productName
     */
    public ProductOutOfStockException(String productName) {
        super(String.format("Product '%s' is currently out of stock", productName));
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
