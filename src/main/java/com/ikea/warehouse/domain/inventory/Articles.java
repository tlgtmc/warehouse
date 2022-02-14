package com.ikea.warehouse.domain.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Articles object
 */
@Data
public class Articles {
    @JsonProperty("inventory")
    private List<Article> articleList;
}
