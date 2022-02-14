package com.ikea.warehouse.service;

import com.ikea.warehouse.domain.inventory.Article;

import java.util.Map;

public interface ArticleService {

    Map<Integer, Article> getArticles();

    Article getArticleById(int id);

    boolean isArticleAvailableById(int id, int amount);
}
