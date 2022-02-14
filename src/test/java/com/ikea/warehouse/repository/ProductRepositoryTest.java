package com.ikea.warehouse.repository;

import com.ikea.warehouse.domain.product.Products;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ikea.warehouse.util.TestUtil.buildProductsObject;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void test_addProducts() {
        Products products = buildProductsObject();

        productRepository.addProducts(products);

        assertThat(productRepository.getProducts().get(products.getProductList().get(0).getName()))
                .isEqualTo(products.getProductList().get(0));
        assertThat(productRepository.getProducts().size()).isEqualTo(products.getProductList().size());
    }

    @Test
    void test_getProductByProductName() {
        Products products = buildProductsObject();

        productRepository.addProducts(products);

        assertThat(productRepository.getProduct(products.getProductList().get(0).getName()))
                .isEqualTo(products.getProductList().get(0));
        assertThat(productRepository.getProduct(products.getProductList().get(0).getName()).getName())
                .isEqualTo(products.getProductList().get(0).getName());

    }

}
