package com.fushaoqin.jianshu.controllers;

import com.fushaoqin.jianshu.common.Result;
import com.fushaoqin.jianshu.model.Dto.ArticleDto;
import com.fushaoqin.jianshu.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "http://localhost:3000")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/trendingList/{page}/{limit}")
    public Result hotSearch(@PathVariable long page,
                            @PathVariable long limit) {
        //TODO: use Redis to store trending list
        List<String> list = Arrays.asList(
                "人工智能", "加密货币", "远程工作", "可持续生活", "电动汽车",
                "5G 技术", "量子计算", "增强现实", "素食主义", "太空探索",
                "区块链技术", "智能家居", "自动驾驶汽车", "机器学习", "虚拟现实",
                "健康科技", "无人机技术", "大数据", "云计算", "生物技术", "可穿戴技术",
                "智能城市", "网络安全", "数据隐私", "清洁能源", "新冠疫苗", "社交媒体趋势",
                "数字货币", "电子商务", "深度学习", "人脸识别技术", "边缘计算", "物联网",
                "AIoT", "即时通讯软件", "在线教育", "数字化转型", "NFTs（非同质化代币）",
                "3D 打印技术", "机器人技术", "精准医疗", "基因编辑", "可再生能源",
                "气候变化", "环境保护", "智能制造", "金融科技", "数字健康", "游戏化教育", "元宇宙"
        );
        int size = list.size();
        list = list.subList((int) ((page - 1) * limit), (int) (page * limit));
        Map result = new HashMap();
        result.put("records", list);
        result.put("total", size);
        return Result.ok(result);
    }

    @GetMapping("/keyword/{keyword}")
    public Result search(@PathVariable String keyword) {
        List<ArticleDto> list = searchService.search(keyword);
        return Result.ok(list);
    }

}
