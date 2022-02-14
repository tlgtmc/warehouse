package com.ikea.warehouse.domain.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

/**
 * Product object
 */
@Data
public class Product {

    private String name;

    @JsonProperty(value = "contain_articles")
    private Set<ProductArticle> articles;

    private int stockCount;
}
