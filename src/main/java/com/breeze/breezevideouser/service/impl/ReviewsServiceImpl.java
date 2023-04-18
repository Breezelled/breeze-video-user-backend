package com.breeze.breezevideouser.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.breeze.breezevideouser.domain.Reviews;
import com.breeze.breezevideouser.mapper.ReviewsMapper;
import com.breeze.breezevideouser.service.ReviewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.breezevideouser.utils.YamlUtil;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 电影评论 服务实现类
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
@Service
public class ReviewsServiceImpl extends ServiceImpl<ReviewsMapper, Reviews> implements ReviewsService {

    @Override
    public Double sentimentAnalysis(String content) {
        String url = YamlUtil.getStringByYaml("tensorflow-serving-bert.url");
        Map<String, String[]> map = new ConcurrentHashMap<>();
        map.put("instances", new String[]{content});
        String data = JSONUtil.toJsonStr(map);

        HttpResponse response = HttpRequest.post(url)
                .contentType("application/json")
                .body(data)
                .execute();

        JSONObject json = JSONUtil.parseObj(response.body());

        JSONArray predictions = json.getJSONArray("predictions");

        // sigmoid
        Double value = 1 / (1 + Math.exp(-predictions.getJSONArray(0).getDouble(0)));
        DecimalFormat df = new DecimalFormat("#.######");
        String formattedValue = df.format(value);
        return Double.parseDouble(formattedValue);
    }
}
