package com.ikea.warehouse.util;

import com.ikea.warehouse.domain.inventory.Article;
import com.ikea.warehouse.domain.inventory.Articles;
import com.ikea.warehouse.domain.product.Product;
import com.ikea.warehouse.domain.product.ProductArticle;
import com.ikea.warehouse.domain.product.Products;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TestUtil {

    public static final String GET_ARTICLES_ENDPOINT = "/articles";

    public static final String GET_PRODUCTS_ENDPOINT = "/products";
    public static final String GET_PRODUCT_BY_NAME_ENDPOINT = "/products/{productName}";

    public final static String PRODUCT_NAME = "Dining Table";

    public static Articles buildArticlesObject() {
        Articles articles = new Articles();
        articles.setArticleList(Collections.singletonList(buildArticleObject()));
        return articles;
    }

    public static Article buildArticleObject() {
        Article article = new Article();
        article.setId(1);
        article.setStock(20);
        article.setName("Screw");
        return article;
    }

    public static Products buildProductsObject() {
        Products products = new Products();


        Set<ProductArticle> productArticles = new HashSet<>();
        productArticles.add(buildProductArticle());

        Product product = buildProductObject();
        product.setArticles(productArticles);

        products.setProductList(Collections.singletonList(product));
        return products;
    }

    public static Product buildProductObject() {
        Product product = new Product();
        product.setName("Dining Table");
        product.setStockCount(2);
        return product;
    }

    public static ProductArticle buildProductArticle() {
        ProductArticle productArticle = new ProductArticle();
        productArticle.setArticleId(1);
        productArticle.setAmount(3);
        return productArticle;
    }

    public static Map<String, Product> getProductsMap() {
        return buildProductsObject()
                .getProductList()
                .stream()
                .collect(Collectors.toMap(Product::getName, product -> product));
    }

    public static Map<Integer, Article> getArticlesMap() {
        return buildArticlesObject()
                .getArticleList()
                .stream()
                .collect(Collectors.toMap(Article::getId, article -> article));
    }
}
