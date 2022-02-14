package com.ikea.warehouse.domain.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ProductArticle object
 */
@Data
public class ProductArticle {

    @JsonProperty("art_id")
    private int articleId;

    @JsonProperty("amount_of")
    private int amount;
}
