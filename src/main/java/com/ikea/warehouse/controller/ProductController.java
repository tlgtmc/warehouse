package com.ikea.warehouse.controller;

import com.ikea.warehouse.domain.product.Product;
import com.ikea.warehouse.service.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Return all products
     *
     * @return Map of products, Map<String, Product>>
     */
    @GetMapping
    public ResponseEntity<Map<String, Product>> getAllProducts() {
        log.info("All products requested");
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    /**
     * Finds and returns the requested product by name
     *
     * @return The product
     */
    @GetMapping(value = "/{productName}")
    public ResponseEntity<Product> getProduct(@PathVariable @NonNull String productName) {
        log.info("Product '{}' requested", productName);

        return new ResponseEntity<>(productService.getProduct(productName), HttpStatus.OK);
    }

    /**
     * Sells requested product by name depending on the stock availability
     *
     * @return
     */
    @PutMapping(value = "/{productName}")
    public void sellProduct(@PathVariable @NonNull String productName) {
        log.info("Purchase request received for product '{}' ", productName);
        productService.sellProduct(productName);
    }
}
