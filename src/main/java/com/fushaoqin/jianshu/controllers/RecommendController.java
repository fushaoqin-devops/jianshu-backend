package com.fushaoqin.jianshu.controllers;

import com.fushaoqin.jianshu.common.Result;
import com.fushaoqin.jianshu.model.Recommend;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/recommend")
@CrossOrigin(origins = "http://localhost:3000")
public class RecommendController {

    @GetMapping("/list")
    public Result list() {
        // Not really implemented, just return a list of hard-coded data
        List<Recommend> list = Arrays.asList(new Recommend(1, "/banner1.png"),
                new Recommend(2, "/banner2.png"),
                new Recommend(3, "/banner3.png"),
                new Recommend(4, "/banner4.png"));
        return Result.ok(list);
    }

}
