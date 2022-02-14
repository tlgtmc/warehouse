package com.ikea.warehouse.event;

import com.ikea.warehouse.domain.product.Product;
import org.springframework.context.ApplicationEvent;

public class ProductSellEvent extends ApplicationEvent {

    /**
     * An event that is published when a product sell request received
     *
     * @param product that is being sold
     */
    public ProductSellEvent(Product product) {
        super(product);
    }
}
