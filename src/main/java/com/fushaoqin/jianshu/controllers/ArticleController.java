package com.fushaoqin.jianshu.controllers;

import com.fushaoqin.jianshu.common.Result;
import com.fushaoqin.jianshu.model.Article;
import com.fushaoqin.jianshu.model.Dto.ArticleDto;
import com.fushaoqin.jianshu.service.ArticleService;
import com.fushaoqin.jianshu.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/article")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AuthService authService;

    @GetMapping("/list/{page}")
    public Result list(@PathVariable long page) {
        // TODO: use MyBatis Pagination
        List<ArticleDto> res = articleService.getAllArticles();
        if (page > res.size() / 4) {
            return Result.ok(new ArrayList<>());
        }
        res = res.subList((int) ((page - 1) * 4), (int) (page * 4));

        return Result.ok(res);
    }

    @GetMapping("/{id}")
    public Result getArticle(@PathVariable long id) {
        ArticleDto article = articleService.getArticleById(id);
        return Result.ok(article);
    }

    @PostMapping("/add")
    public Result addArticle(@RequestBody Article article, HttpServletRequest request) {
        Long userId = authService.getUserId(request);
        Long articleId = articleService.addArticle(article, userId);
        return Result.ok(articleId);
    }

    @GetMapping("/userArticle/{id}")
    public Result getUserArticle(@PathVariable long id) {
        List<ArticleDto> list = articleService.getUserArticle(id);
        return Result.ok(list);
    }

}
