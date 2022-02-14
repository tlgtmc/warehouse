package com.ikea.warehouse.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ikea.warehouse.domain.inventory.Articles;
import com.ikea.warehouse.repository.ArticleRepository;
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
public final class ArticleLoader extends FileLoader {

    private final ArticleRepository inventoryRepository;

    @Value("${warehouse.inventory.file.path}")
    private String inventoryFilePath;

    @Bean
    CommandLineRunner initiateInventory() {
        return args -> loadInventory();
    }

    /**
     * Loads initial inventory records to the system
     */
    private void loadInventory() {
        log.info("Loading inventories from file...");

        try {
            Articles inventoryList = loadFileAsObject(inventoryFilePath, new TypeReference<>() {
            });
            inventoryRepository.addArticles(inventoryList);
            log.info("Inventories successfully loaded from file!");
        } catch (IOException ex) {
            log.error(String.format("Error while loading inventory file: %s", ex));
        }
    }
}
