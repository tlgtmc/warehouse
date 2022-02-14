package com.ikea.warehouse.service.impl;

import com.ikea.warehouse.domain.inventory.Article;
import com.ikea.warehouse.domain.product.Product;
import com.ikea.warehouse.domain.product.ProductArticle;
import com.ikea.warehouse.event.publisher.WarehouseEventPublisher;
import com.ikea.warehouse.exception.ProductNotFoundException;
import com.ikea.warehouse.exception.ProductOutOfStockException;
import com.ikea.warehouse.repository.ProductRepository;
import com.ikea.warehouse.service.ArticleService;
import com.ikea.warehouse.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ArticleService articleService;
    private final WarehouseEventPublisher eventPublisher;

    /**
     * Returns all products
     *
     * @return
     */
    @Override
    public Map<String, Product> getProducts() {
        Map<String, Product> products = productRepository.getProducts();
        products.values().stream().forEach(product -> {
            product.setStockCount(0);
            setProductStockInfo(product);
        });
        return products;
    }

    /**
     * Sets product stock information
     *
     * @param product
     */
    private void setProductStockInfo(Product product) {
        for (ProductArticle productArticle : product.getArticles()) {
            Article article = articleService.getArticleById(productArticle.getArticleId());
            int stock = article.getStock() / productArticle.getAmount();
            if (stock == 0) {
                product.setStockCount(0);
                break;
            } else if (product.getStockCount() == 0 || product.getStockCount() > stock) {
                product.setStockCount(stock);
            }
        }
    }

    /**
     * Finds and returns requested product by name
     *
     * @param productName
     * @return
     */
    @Override
    public Product getProduct(String productName) {
        Product product = productRepository.getProduct(productName);
        if (product == null) {
            throw new ProductNotFoundException(productName);
        }
        setProductStockInfo(product);
        return product;
    }

    /**
     * Sells product by the given name
     *
     * @param productName
     * @throws ProductOutOfStockException
     */
    @Override
    public void sellProduct(String productName) {
        Product product = getProduct(productName);
        if (isProductArticlesAvailable(product)) {
            eventPublisher.publishSellEvent(product);
        } else {
            throw new ProductOutOfStockException(productName);
        }
    }

    /**
     * Checks whether given product's articles are available or not
     *
     * @param product
     * @return true or false depending on the result
     */
    private boolean isProductArticlesAvailable(Product product) {
        AtomicBoolean isAvailable = new AtomicBoolean(true);
        product.getArticles().stream().forEach(article -> {
            if (!articleService.isArticleAvailableById(article.getArticleId(), article.getAmount())) {
                isAvailable.set(false);
            }
        });
        return isAvailable.get();
    }
}
