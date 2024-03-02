package com.fushaoqin.jianshu.model.Dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String account;

    private String password;

}
