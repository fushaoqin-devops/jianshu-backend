package com.fushaoqin.jianshu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fushaoqin.jianshu.common.Constant;
import com.fushaoqin.jianshu.mapper.ArticleMapper;
import com.fushaoqin.jianshu.model.Article;
import com.fushaoqin.jianshu.model.ArticleTest;
import com.fushaoqin.jianshu.model.Dto.ArticleDto;
import com.fushaoqin.jianshu.model.User;
import com.fushaoqin.jianshu.service.ArticleService;
import com.fushaoqin.jianshu.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ArticleDto getArticleById(Long id) {
        Article article = getById(id);
        String createString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(article.getCreateTime());
        String updateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(article.getUpdateTime());
        ArticleDto articleDto = new ArticleDto();
        if (article != null) {
            BeanUtils.copyProperties(article, articleDto);
            articleDto.setAuthor(userService.getById(article.getAuthorId()).getName());
            articleDto.setWordCount(article.getContent().length());
            articleDto.setCreateString(createString);
            articleDto.setUpdateString(updateString);
        }

        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getAuthorId, article.getAuthorId());
        wrapper.ne(Article::getId, article.getId());
        wrapper.orderByDesc(Article::getUpdateTime);
        List<Article> articles = list(wrapper);
        Map<Long, String> others = articles.stream().collect(
                HashMap::new,
                (map, a) -> map.put(a.getId(), a.getTitle()),
                HashMap::putAll
        );
        articleDto.setOthers(others);
        return articleDto;
    }

    @Override
    public Long addArticle(Article article, Long userId) {
        article.setAuthorId(userId);
        save(article);
        return article.getId();
    }

    @Override
    public List<ArticleDto> getArticleListByKeyword(String keyword) {
        List<ArticleDto> articleDtos = articleMapper.selectArticlesWithKeyword(keyword);
        articleDtos.stream().forEach(
                a -> a.setDesc(a.getContent().substring(0, Math.min(a.getContent().length(), 80)) + "...")
        );
        return articleDtos;
    }

    @Override
    public List<ArticleDto> getAllArticles() {
        List<ArticleDto> articles = articleMapper.selectArticles();
        articles.stream().forEach(
                a -> a.setDesc(a.getContent().substring(0, Math.min(a.getContent().length(), 80)) + "...")
        );
        return articles;
    }

    @Override
    public Integer getWordCount(Long id) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getAuthorId, id);
        List<Article> articles = list(wrapper);
        return articles.stream().map(Article::getContent).mapToInt(String::length).sum();
    }

    @Override
    public List<ArticleDto> getUserArticle(long id) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getAuthorId, id);
        List<Article> articles = list(wrapper);
        List<ArticleDto> articleDtos = articles.stream().map(
                a -> {
                    ArticleDto articleDto = new ArticleDto();
                    BeanUtils.copyProperties(a, articleDto);
                    articleDto.setDesc(a.getContent().substring(0, Math.min(a.getContent().length(), 80)) + "...");
                    articleDto.setAuthor(userService.getById(a.getAuthorId()).getName());
                    return articleDto;
                }
        ).toList();
        return articleDtos;
    }

}
