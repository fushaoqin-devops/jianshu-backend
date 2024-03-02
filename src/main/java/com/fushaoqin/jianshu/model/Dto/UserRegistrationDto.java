package com.fushaoqin.jianshu.model.Dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegistrationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    String name;

    String phone;

    String password;

}
