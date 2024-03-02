package com.fushaoqin.jianshu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fushaoqin.jianshu.model.Article;
import com.fushaoqin.jianshu.model.Dto.ArticleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("SELECT a.*, au.name as author FROM article a " +
            "JOIN user au ON a.author_id = au.id " +
            "WHERE a.title LIKE CONCAT('%', #{keyword}, '%') " +
            "OR au.name LIKE CONCAT('%', #{keyword}, '%')")
    List<ArticleDto> selectArticlesWithKeyword(@Param("keyword") String keyword);

    @Select("SELECT a.*, au.name as author FROM article a " +
            "JOIN user au ON a.author_id = au.id " +
            "ORDER BY a.update_time DESC")
    List<ArticleDto> selectArticles();

}
