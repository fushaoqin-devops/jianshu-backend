package com.fushaoqin.jianshu.model.Dto;

import lombok.Data;

@Data
public class UserInfoDto {
    private Long id;
    private String name;
    private String avatar;
    private Integer wordCount;
    private boolean isFollowed;

}
