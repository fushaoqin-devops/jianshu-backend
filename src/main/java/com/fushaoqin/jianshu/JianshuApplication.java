package com.fushaoqin.jianshu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class JianshuApplication {

    public static void main(String[] args) {
        SpringApplication.run(JianshuApplication.class, args);
    }

}
