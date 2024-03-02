package com.fushaoqin.jianshu.model;

import lombok.Data;

@Data
public class Recommend {
    Integer id;
    String url;

    public Recommend(Integer id, String url) {
        this.id = id;
        this.url = url;
    }

}
