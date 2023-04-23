package com.breeze.breezevideouser.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.domain.Favorites;
import com.breeze.breezevideouser.domain.Info;
import com.breeze.breezevideouser.domain.Likes;
import com.breeze.breezevideouser.domain.dto.RecommendDto;
import com.breeze.breezevideouser.mapper.InfoMapper;
import com.breeze.breezevideouser.service.FavoritesService;
import com.breeze.breezevideouser.service.InfoService;
import com.breeze.breezevideouser.service.LikesService;
import com.breeze.breezevideouser.utils.YamlUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author breeze
 * @date 2023/4/23 13:23
 */
@SpringBootTest
class InfoServiceImplTest {

    @Autowired
    private InfoService infoService;

    @Test
    void personalizedRecommendation() {
//        List<String> list = Arrays.asList("apple", "banana", "pear", "orange", "lemon");
//        List<Integer> sortOrder = Arrays.asList(3, 1, 4, 2, 5);
//        List<String> sortedList = IntStream.range(0, list.size())
//                .boxed()
//                .sorted(Comparator.comparingInt(sortOrder::get).reversed())
//                .map(list::get)
//                .collect(Collectors.toList());
//        System.out.println(sortedList);
        String interestType = "Action|Drama|Thriller|Crime";
        String userId = "05bb3687-5f7b-4aee-8e6f-e806363fcc5a";
        Integer id = 5;
        String gender = "M";
        infoService.personalizedRecommendation(userId);
    }
}