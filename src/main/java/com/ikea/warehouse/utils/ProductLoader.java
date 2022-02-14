package com.ikea.warehouse.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ikea.warehouse.domain.product.Products;
import com.ikea.warehouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public final class ProductLoader extends FileLoader {

    private final ProductRepository productRepository;

    @Value("${warehouse.product.file.path}")
    private String productsFilePath;

    @Bean
    CommandLineRunner initiateProducts() {
        return args -> loadProducts();
    }

    /**
     * Loads initial product records to the system
     */
    private void loadProducts() {
        log.info("Loading products from file...");

        try {
            Products products = loadFileAsObject(productsFilePath, new TypeReference<>() {
            });
            productRepository.addProducts(products);
            log.info("Products successfully loaded from file!");
        } catch (IOException ex) {
            log.error(String.format("Error while loading product file: %s", ex));
        }
    }
}
