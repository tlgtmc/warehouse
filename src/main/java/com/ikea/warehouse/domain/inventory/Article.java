package com.ikea.warehouse.domain.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Article object
 */
@Data
public class Article {

    @JsonProperty("art_id")
    private int id;
    private int stock;
    private String name;
}