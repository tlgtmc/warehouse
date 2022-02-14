package com.ikea.warehouse.service;

import com.ikea.warehouse.domain.inventory.Article;
import com.ikea.warehouse.repository.ArticleRepository;
import com.ikea.warehouse.service.impl.ArticleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static com.ikea.warehouse.util.TestUtil.buildArticleObject;
import static com.ikea.warehouse.util.TestUtil.getArticlesMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    void test_getAllArticles_success() {

        Map<Integer, Article> articleMap = getArticlesMap();
        when(articleRepository.getArticles()).thenReturn(articleMap);

        Map<Integer, Article> articles = articleService.getArticles();

        verify(articleRepository).getArticles();
        assertThat(articles).isNotNull();
        assertThat(articles.size()).isEqualTo(articleMap.size());
    }


    @Test
    void test_getArticleById_success() {
        Article article = buildArticleObject();
        when(articleRepository.getArticle(article.getId())).thenReturn(article);

        Article articleResponse = articleService.getArticleById(article.getId());

        verify(articleRepository).getArticle(anyInt());
        assertThat(articleResponse).isNotNull();
        assertThat(articleResponse).isEqualTo(article);
    }

    @Test
    void test_getArticleById_notFound() {
        when(articleRepository.getArticle(anyInt())).thenReturn(null);

        Article articleResponse = articleService.getArticleById(1);

        verify(articleRepository).getArticle(anyInt());
        assertThat(articleResponse).isNull();
    }

    @Test
    void test_isArticleAvailableById_available() {
        Map<Integer, Article> articleMap = getArticlesMap();
        when(articleRepository.getArticles()).thenReturn(articleMap);

        boolean isArticleAvailable = articleService.isArticleAvailableById(articleMap.get(1).getId(), 2);

        verify(articleRepository).getArticles();
        assertThat(isArticleAvailable).isTrue();
    }

    @Test
    void test_isArticleAvailableById_notAvailable() {
        Map<Integer, Article> articleMap = getArticlesMap();
        when(articleRepository.getArticles()).thenReturn(articleMap);

        boolean isArticleAvailable = articleService.isArticleAvailableById(articleMap.get(1).getId(), 200);

        verify(articleRepository).getArticles();
        assertThat(isArticleAvailable).isFalse();
    }

    @Test
    void test_isArticleAvailableById_notFound() {
        Map<Integer, Article> emptyMap = new HashMap<>();
        when(articleRepository.getArticles()).thenReturn(emptyMap);

        boolean isArticleAvailable = articleService.isArticleAvailableById(1, 1);

        verify(articleRepository).getArticles();
        assertThat(isArticleAvailable).isFalse();
    }
}
