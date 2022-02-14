package com.ikea.warehouse.repository;

import com.ikea.warehouse.domain.inventory.Article;
import com.ikea.warehouse.domain.inventory.Articles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ArticleRepository {

    private Map<Integer, Article> articlesMap = new HashMap<>();

    /**
     * Adds articles
     *
     * @param articles
     */
    public void addArticles(Articles articles) {
        this.articlesMap = articles.getArticleList().stream().collect(Collectors.toMap(Article::getId, article -> article));
    }

    /**
     * Returns all articles
     *
     * @return
     */
    public Map<Integer, Article> getArticles() {
        return this.articlesMap;
    }

    /**
     * Returns requested article by id
     *
     * @param id
     * @return
     */
    public Article getArticle(int id) {
        return this.articlesMap.get(id);
    }
}
