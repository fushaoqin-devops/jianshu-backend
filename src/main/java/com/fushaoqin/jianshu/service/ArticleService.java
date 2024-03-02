package com.fushaoqin.jianshu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fushaoqin.jianshu.model.Article;
import com.fushaoqin.jianshu.model.ArticleTest;
import com.fushaoqin.jianshu.model.Dto.ArticleDto;

import java.util.List;

public interface ArticleService extends IService<Article> {

    ArticleDto getArticleById(Long id);

    Long addArticle(Article article, Long userId);

    List<ArticleDto> getArticleListByKeyword(String keyword);

    List<ArticleDto> getAllArticles();

    Integer getWordCount(Long id);

    List<ArticleDto> getUserArticle(long id);

}
