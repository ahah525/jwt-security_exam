package com.ll.exam.app__2022_10_05.app.article.controller;

import com.ll.exam.app__2022_10_05.app.article.entity.Article;
import com.ll.exam.app__2022_10_05.app.article.service.ArticleService;
import com.ll.exam.app__2022_10_05.app.base.dto.RsData;
import com.ll.exam.app__2022_10_05.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    // 게시글 리스트 조회
    @GetMapping("")
    public ResponseEntity<RsData> list() {
        List<Article> articles = articleService.findAll();

        return Util.spring.responseEntityOf(
                RsData.successOf(
                        Util.mapOf("articles", articles)
                )
        );
    }
}
