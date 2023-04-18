package com.breeze.breezevideouser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.domain.Favorites;
import com.breeze.breezevideouser.domain.Likes;
import com.breeze.breezevideouser.domain.Reviews;
import com.breeze.breezevideouser.domain.Users;
import com.breeze.breezevideouser.service.FavoritesService;
import com.breeze.breezevideouser.service.ReviewsService;
import com.breeze.breezevideouser.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author breeze
 * @date 2023/4/18 13:10
 */
@SpringBootTest
class FavoritesControllerTest {

    @Autowired
    private ReviewsService reviewsService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private FavoritesService favoritesService;

    @Test
    void insertLikes() {
        QueryWrapper<Reviews> reviewsQueryWrapper = new QueryWrapper<>();
        reviewsQueryWrapper.ge("sentiment_rating", 0.9985);
        List<Reviews> list = reviewsService.list(reviewsQueryWrapper);
        for (Reviews r:list) {
            QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", r.getAuthor());
            Users user = usersService.getOne(queryWrapper);
            Favorites favorites = new Favorites();
            favorites.setFavoriteTime(LocalDateTime.now());
            favorites.setUserId(user.getUserid());
            favorites.setMovieId(r.getMovieId());
            favoritesService.save(favorites);
        }
        System.out.println(list.size());
    }
}