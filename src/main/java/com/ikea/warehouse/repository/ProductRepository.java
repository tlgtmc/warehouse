package com.ikea.warehouse.repository;

import com.ikea.warehouse.domain.product.Product;
import com.ikea.warehouse.domain.product.Products;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ProductRepository {

    private Map<String, Product> productsMap = new HashMap<>();

    /**
     * Adds products
     *
     * @param products
     */
    public void addProducts(Products products) {
        this.productsMap = products.getProductList().stream().collect(Collectors.toMap(Product::getName, product -> product));
    }

    /**
     * Returns all products
     *
     * @return
     */
    public Map<String, Product> getProducts() {
        return this.productsMap;
    }

    /**
     * Returns requested product by name
     *
     * @param productName
     * @return
     */
    public Product getProduct(String productName) {
        return this.productsMap.get(productName);
    }
}
