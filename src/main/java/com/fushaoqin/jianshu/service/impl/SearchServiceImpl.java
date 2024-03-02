package com.fushaoqin.jianshu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fushaoqin.jianshu.model.Article;
import com.fushaoqin.jianshu.model.Dto.ArticleDto;
import com.fushaoqin.jianshu.service.ArticleService;
import com.fushaoqin.jianshu.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ArticleService articleService;

    @Override
    public List<ArticleDto> search(String keyword) {
        return articleService.getArticleListByKeyword(keyword);
    }

}
