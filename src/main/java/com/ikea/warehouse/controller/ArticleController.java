package com.ikea.warehouse.controller;

import com.ikea.warehouse.domain.inventory.Article;
import com.ikea.warehouse.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * Return all articles
     *
     * @return ResponseEntity<Map < Integer, Article>>
     */
    @GetMapping
    public ResponseEntity<Map<Integer, Article>> getArticles() {
        log.info("All articles requested");
        return new ResponseEntity<>(articleService.getArticles(), HttpStatus.OK);
    }
}