package com.breeze.breezevideouser.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.domain.Favorites;
import com.breeze.breezevideouser.domain.Info;
import com.breeze.breezevideouser.domain.Likes;
import com.breeze.breezevideouser.domain.Users;
import com.breeze.breezevideouser.domain.dto.RecommendDto;
import com.breeze.breezevideouser.domain.vo.InfoVo;
import com.breeze.breezevideouser.domain.vo.TypeVo;
import com.breeze.breezevideouser.mapper.InfoMapper;
import com.breeze.breezevideouser.service.FavoritesService;
import com.breeze.breezevideouser.service.InfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.breezevideouser.service.LikesService;
import com.breeze.breezevideouser.service.UsersService;
import com.breeze.breezevideouser.utils.YamlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>
 * 电影信息 服务实现类
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements InfoService {

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private LikesService likesService;

    @Autowired
    private FavoritesService favoritesService;

    @Autowired
    private UsersService usersService;

    @Cacheable(value = "topRated", key = "'topRated.json'")
    @Override
    public List<InfoVo> topRated() {
        return infoMapper.topRated();
    }

    @Override
    public List<InfoVo> topRatedAndNumByType(String type, Integer limit) {
        return infoMapper.topRatedAndNumByType(type, limit);
    }

    @Cacheable(value = "topNumType", key = "'topNumType.json'")
    @Override
    public List<TypeVo> topNumType(Integer countLimit) {
        List<TypeVo> list = infoMapper.topNumType(countLimit);
        for (TypeVo t:list) {
            List<InfoVo> temp = infoMapper.topRatedAndNumByType(t.getType(), 1);
            t.setPosterUrl(temp.get(0).getPosterUrl());
            t.setName(temp.get(0).getName());
        }
        return list;
    }

    @Override
    public List<InfoVo> userFavorites(String userId) {
        return infoMapper.userFavorites(userId);
    }

    @Override
    public List<InfoVo> personalizedRecommendation(String userId) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userId);
        Users users = usersService.getOne(queryWrapper);
        List<InfoVo> list = infoMapper.getInfoByTypes(users.getInterestType().split("\\|"));
        Map<String, Object> userIdMap = new ConcurrentHashMap<>(1);
        userIdMap.put("user_id", userId);
        List<Likes> likesList = likesService.listByMap(userIdMap);
        List<Favorites> favoritesList = favoritesService.listByMap(userIdMap);
        String url = YamlUtil.getStringByYaml("tensorflow-serving-din.url");
        Map<String, List<RecommendDto>> map = new ConcurrentHashMap<>();
        List<RecommendDto> recommendList = new ArrayList<>();
        for (InfoVo i : list) {
            StringJoiner likeJoiner = new StringJoiner("|");
            likesList.forEach(likes -> likeJoiner.add(String.valueOf(likes.getMovieId())));
            String likeSequence = likeJoiner.toString();
            StringJoiner favoJoiner = new StringJoiner("|");
            favoritesList.forEach(favos -> favoJoiner.add(String.valueOf(favos.getMovieId())));
            String favoSequence = favoJoiner.toString();
            RecommendDto recommendDto =
                    new RecommendDto(users.getId(), likeSequence, users.getInterestType(),
                            i.getId(), i.getType(), favoSequence, users.getGender());
            recommendList.add(recommendDto);
        }
        map.put("instances", recommendList);
        String data = JSONUtil.toJsonStr(map);

        HttpResponse response = HttpRequest.post(url)
                .contentType("application/json")
                .body(data)
                .execute();

        JSONObject json = JSONUtil.parseObj(response.body());

        System.out.println(response.body());

        JSONArray predictions = json.getJSONArray("predictions");

        List<Double> predictionList = predictions.toList(Double.class);

        return IntStream.range(0, list.size())
                .boxed()
                .sorted(Comparator.comparingDouble(predictionList::get).reversed())
                .map(list::get)
                .collect(Collectors.toList());
    }
}
