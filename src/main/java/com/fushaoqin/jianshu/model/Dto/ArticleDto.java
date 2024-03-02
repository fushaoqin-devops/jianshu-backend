package com.fushaoqin.jianshu.model.Dto;

import com.fushaoqin.jianshu.model.Article;
import lombok.Data;

import java.util.Map;

@Data
public class ArticleDto extends Article {

    private String author;
    private Integer wordCount;
    private String createString;
    private String updateString;
    private String desc;
    private Map<Long, String> others;

}
