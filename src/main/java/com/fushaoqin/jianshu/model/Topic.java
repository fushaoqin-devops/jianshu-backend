package com.fushaoqin.jianshu.model;

import lombok.Data;

@Data
public class Topic {
    Integer id;
    String title;
    String imgUrl;

    public Topic(int id, String title, String imgUrl) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
    }

}
