package com.breeze.breezevideouser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.domain.Likes;
import com.breeze.breezevideouser.domain.Reviews;
import com.breeze.breezevideouser.domain.Users;
import com.breeze.breezevideouser.service.InfoService;
import com.breeze.breezevideouser.service.LikesService;
import com.breeze.breezevideouser.service.ReviewsService;
import com.breeze.breezevideouser.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author breeze
 * @date 2023/4/18 12:42
 */
@SpringBootTest
class LikesControllerTest {

    @Autowired
    private LikesService likesService;
    @Autowired
    private InfoService infoService;
    @Autowired
    private ReviewsService reviewsService;
    @Autowired
    private UsersService usersService;
    @Test
    void userLikes() {
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", "8b3f4f7f-ea1c-4ded-80f8-f49c5ccfa0d0");
        queryWrapper.orderByDesc("like_time");
        List<Likes> list = likesService.list(queryWrapper);
        List<Integer> ids = new ArrayList<>();
        for (Likes l: list) {
            ids.add(l.getMovieId());
        }
        System.out.println(list);
    }

    @Test
    void insertLikes() {
        QueryWrapper<Reviews> reviewsQueryWrapper = new QueryWrapper<>();
        reviewsQueryWrapper.ge("sentiment_rating", 0.9);
        List<Reviews> list = reviewsService.list(reviewsQueryWrapper);
        for (Reviews r:list) {
            QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", r.getAuthor());
            Users user = usersService.getOne(queryWrapper);
            Likes likes = new Likes();
            likes.setLikeTime(LocalDateTime.now());
            likes.setUserId(user.getUserid());
            likes.setMovieId(r.getMovieId());
            likesService.save(likes);
        }
        System.out.println(list.size());
    }
}