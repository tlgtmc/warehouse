package com.ikea.warehouse.event;

import com.ikea.warehouse.domain.product.Product;
import com.ikea.warehouse.domain.product.ProductArticle;
import com.ikea.warehouse.event.publisher.WarehouseEventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static com.ikea.warehouse.util.TestUtil.buildProductArticle;
import static com.ikea.warehouse.util.TestUtil.buildProductObject;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ProductSellEventTest {

    @Autowired
    private WarehouseEventPublisher warehouseEventPublisher;

    @Test
    void test_sellEventPublish() {
        Product product = buildProductObject();
        Set<ProductArticle> productArticles = new HashSet<>();
        productArticles.add(buildProductArticle());
        product.setArticles(productArticles);

        warehouseEventPublisher.publishSellEvent(product);
    }
}
