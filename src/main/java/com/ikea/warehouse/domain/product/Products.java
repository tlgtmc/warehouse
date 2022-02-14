package com.ikea.warehouse.domain.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Products object
 */
@Data
public class Products {

    @JsonProperty("products")
    private List<Product> productList;
}
