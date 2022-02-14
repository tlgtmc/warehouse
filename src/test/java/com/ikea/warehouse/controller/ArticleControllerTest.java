package com.ikea.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.domain.inventory.Article;
import com.ikea.warehouse.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;
import java.util.stream.Collectors;

import static com.ikea.warehouse.util.TestUtil.GET_ARTICLES_ENDPOINT;
import static com.ikea.warehouse.util.TestUtil.buildArticlesObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ArticleController.class)
@ContextConfiguration(classes = ArticleController.class)
public class ArticleControllerTest {

    @MockBean
    private ArticleService articleService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setMockMvc() {
        this.mockMvc = standaloneSetup(new ArticleController(articleService)).build();
    }

    @Test
    void test_getArticles_success() throws Exception {
        Map<Integer, Article> articleMap = buildArticlesObject()
                .getArticleList()
                .stream()
                .collect(Collectors.toMap(Article::getId, article -> article));

        when(articleService.getArticles()).thenReturn(articleMap);

        MvcResult mvcResult = mockMvc.perform(get(GET_ARTICLES_ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        assertEquals(new ObjectMapper().writeValueAsString(articleMap), result);
        verify(articleService).getArticles();
    }
}
