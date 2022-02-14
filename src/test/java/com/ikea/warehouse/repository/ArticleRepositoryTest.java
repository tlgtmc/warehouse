package com.ikea.warehouse.repository;

import com.ikea.warehouse.domain.inventory.Articles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ikea.warehouse.util.TestUtil.buildArticlesObject;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void test_addArticles() {
        Articles articles = buildArticlesObject();

        articleRepository.addArticles(articles);

        assertThat(articleRepository.getArticles().get(articles.getArticleList().get(0).getId()))
                .isEqualTo(articles.getArticleList().get(0));
        assertThat(articleRepository.getArticles().size()).isEqualTo(articles.getArticleList().size());
    }

    @Test
    void test_getArticleById() {
        Articles articles = buildArticlesObject();

        articleRepository.addArticles(articles);

        assertThat(articleRepository.getArticle(articles.getArticleList().get(0).getId()))
                .isEqualTo(articles.getArticleList().get(0));
        assertThat(articleRepository.getArticle(articles.getArticleList().get(0).getId()).getName())
                .isEqualTo(articles.getArticleList().get(0).getName());
    }

}
