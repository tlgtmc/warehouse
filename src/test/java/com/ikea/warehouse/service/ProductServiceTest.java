package com.ikea.warehouse.service;

import com.ikea.warehouse.domain.inventory.Article;
import com.ikea.warehouse.domain.product.Product;
import com.ikea.warehouse.domain.product.ProductArticle;
import com.ikea.warehouse.event.publisher.WarehouseEventPublisher;
import com.ikea.warehouse.exception.ProductNotFoundException;
import com.ikea.warehouse.exception.ProductOutOfStockException;
import com.ikea.warehouse.repository.ProductRepository;
import com.ikea.warehouse.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.ikea.warehouse.util.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ArticleService articleService;

    @Mock
    private WarehouseEventPublisher eventPublisher;

    @Test
    void test_getProducts_success() {
        Map<String, Product> productMap = getProductsMap();

        Article article = buildArticleObject();
        when(productRepository.getProducts()).thenReturn(productMap);
        when(articleService.getArticleById(anyInt())).thenReturn(article);

        Map<String, Product> products = productService.getProducts();

        verify(productRepository).getProducts();
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(productMap.size());
    }


    @Test
    void test_getProducts_noStock_success() {
        Map<String, Product> productMap = getProductsMap();

        Article article = buildArticleObject();
        article.setStock(0);
        when(productRepository.getProducts()).thenReturn(productMap);
        when(articleService.getArticleById(anyInt())).thenReturn(article);

        Map<String, Product> products = productService.getProducts();

        verify(productRepository).getProducts();
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(productMap.size());
        assertThat(products.get("Dining Table").getStockCount()).isEqualTo(0);
    }

    @Test
    void test_getProduct_success() {
        Product product = buildProductObject();
        Set<ProductArticle> productArticles = new HashSet<>();
        productArticles.add(buildProductArticle());
        product.setArticles(productArticles);

        when(productRepository.getProduct(product.getName())).thenReturn(product);
        when(articleService.getArticleById(anyInt())).thenReturn(buildArticleObject());

        Product productResponse = productService.getProduct(product.getName());

        verify(productRepository).getProduct(product.getName());
        assertThat(productResponse).isNotNull();
        assertThat(productResponse.getStockCount()).isEqualTo(product.getStockCount());
    }


    @Test
    void test_getProduct_throws_productNotFoundException() {
        Product product = buildProductObject();

        when(productRepository.getProduct(product.getName())).thenReturn(null);

        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(product.getName()));

        verify(productRepository).getProduct(product.getName());
    }

    @Test
    void test_sellProduct_success() {
        Product product = buildProductObject();
        Set<ProductArticle> productArticles = new HashSet<>();
        ProductArticle productArticle = buildProductArticle();
        productArticles.add(buildProductArticle());
        product.setArticles(productArticles);

        when(productRepository.getProduct(product.getName())).thenReturn(product);
        when(articleService.getArticleById(anyInt())).thenReturn(buildArticleObject());
        when(articleService.isArticleAvailableById(productArticle.getArticleId(), productArticle.getAmount())).thenReturn(true);

        productService.sellProduct(product.getName());

        verify(productRepository).getProduct(product.getName());
    }

    @Test
    void test_sellProduct_throws_productOutOfStockException() {
        Product product = buildProductObject();
        Set<ProductArticle> productArticles = new HashSet<>();
        productArticles.add(buildProductArticle());
        product.setArticles(productArticles);

        when(productRepository.getProduct(product.getName())).thenReturn(product);
        when(articleService.getArticleById(anyInt())).thenReturn(buildArticleObject());

        assertThrows(ProductOutOfStockException.class, () -> productService.sellProduct(product.getName()));
        verify(productRepository).getProduct(product.getName());
    }
}
