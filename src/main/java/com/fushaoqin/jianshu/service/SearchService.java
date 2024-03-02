package com.fushaoqin.jianshu.service;

import com.fushaoqin.jianshu.model.Dto.ArticleDto;

import java.util.List;

public interface SearchService {

    List<ArticleDto> search(String keyword);

}
