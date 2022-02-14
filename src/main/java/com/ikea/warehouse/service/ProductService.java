package com.ikea.warehouse.service;

import com.ikea.warehouse.domain.product.Product;

import java.util.Map;

public interface ProductService {

    Map<String, Product> getProducts();

    Product getProduct(String productName);

    void sellProduct(String productName);
}
