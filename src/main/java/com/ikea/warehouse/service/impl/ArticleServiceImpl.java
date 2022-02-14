package com.ikea.warehouse.service.impl;

import com.ikea.warehouse.domain.inventory.Article;
import com.ikea.warehouse.domain.product.Product;
import com.ikea.warehouse.domain.product.ProductArticle;
import com.ikea.warehouse.event.ProductSellEvent;
import com.ikea.warehouse.exception.ProductOutOfStockException;
import com.ikea.warehouse.repository.ArticleRepository;
import com.ikea.warehouse.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    /**
     * Returns all articles
     *
     * @return
     */
    @Override
    public Map<Integer, Article> getArticles() {
        return articleRepository.getArticles();
    }

    /**
     * Finds and returns article by id
     *
     * @param id
     * @return
     */
    @Override
    public Article getArticleById(int id) {
        return articleRepository.getArticle(id);
    }

    /**
     * Checks whether given article id is available by requested amount
     *
     * @param id
     * @param amount
     * @return true or false depending on the result
     */
    @Override
    public boolean isArticleAvailableById(int id, int amount) {
        Map<Integer, Article> articles = articleRepository.getArticles();

        if (articles.containsKey(id)) {
            Article article = articles.get(id);
            return article.getStock() >= amount && article.getStock() >= 0;
        }
        return false;
    }

    /**
     * Product Sell Event Listener method.
     * Checks the availability of product articles and removes them from the stock
     *
     * @param productSellEvent the ProductSellEvent
     */
    @EventListener
    private void handleProductSellEvent(ProductSellEvent productSellEvent) {
        Product product = (Product) productSellEvent.getSource();
        log.info("Event received to sell '{}'", product.getName());
        Set<ProductArticle> articles = product.getArticles();
        for (ProductArticle productArticle : articles) {
            if (isArticleAvailableById(productArticle.getArticleId(), productArticle.getAmount())) {
                removeArticleFromStock(productArticle);
            } else {
                throw new ProductOutOfStockException(product.getName());
            }
        }
    }

    /**
     * Updates the inventory and removes the sold product's articles from the stock
     *
     * @param productArticle the product article object
     */
    private void removeArticleFromStock(ProductArticle productArticle) {
        Article article = getArticleById(productArticle.getArticleId());
        log.info("Updating inventory: {} {} sold", productArticle.getAmount(), article.getName());
        article.setStock(article.getStock() - productArticle.getAmount());
    }
}
