package com.breeze.breezevideouser.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.breeze.breezevideouser.domain.Reviews;
import com.breeze.breezevideouser.service.ReviewsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author breeze
 * @date 2023/4/17 00:10
 */
@SpringBootTest
class BertControllerTest {

    @Autowired
    private ReviewsService reviewsService;

    @Test
    void analysis() {
        QueryWrapper<Reviews> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("content", "id");
        queryWrapper.orderBy(true, true, "id");
        queryWrapper.isNull("sentiment_rating");
        List<Reviews> reviewsList = reviewsService.list(queryWrapper);
        for (Reviews review: reviewsList) {
            String url = "http://localhost:8501/v1/models/imdb_bert_wwm_batch12_lr2e-5_epoch2:predict";
            Map<String, String[]> map = new ConcurrentHashMap<>();
            map.put("instances", new String[]{review.getContent()});
            String data = JSONUtil.toJsonStr(map);

            HttpResponse response = HttpRequest.post(url)
                    .contentType("application/json")
                    .body(data)
                    .execute();

            JSONObject json = JSONUtil.parseObj(response.body());

            System.out.println(response.body());

            JSONArray predictions = json.getJSONArray("predictions");

            // sigmoid
            double value = 1 / (1 + Math.exp(-predictions.getJSONArray(0).getDouble(0)));
            DecimalFormat df = new DecimalFormat("#.######");
            String formattedValue = df.format(value);

            double sentimentRating = Double.parseDouble(formattedValue);
            System.out.println("====================================================");
            System.out.println("====================================================");
            System.out.println("=========================" + sentimentRating + "===========================");
            System.out.println("====================================================");
            System.out.println("====================================================");

            UpdateWrapper<Reviews> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("sentiment_rating", sentimentRating);
            updateWrapper.eq("id", review.getId());
            reviewsService.update(updateWrapper);
        }
//        System.out.println(value);
    }
}