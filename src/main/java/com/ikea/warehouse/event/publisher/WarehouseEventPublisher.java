package com.ikea.warehouse.event.publisher;

import com.ikea.warehouse.domain.product.Product;
import com.ikea.warehouse.event.ProductSellEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WarehouseEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Product sell event publisher
     *
     * @param product that is being sold
     */
    public void publishSellEvent(Product product) {
        log.info("Product '{}' purchase in progress!", product.getName());
        ProductSellEvent warehouseEvent = new ProductSellEvent(product);
        eventPublisher.publishEvent(warehouseEvent);
    }
}
