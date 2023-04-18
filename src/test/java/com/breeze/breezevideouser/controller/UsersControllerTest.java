package com.breeze.breezevideouser.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.breeze.breezevideouser.domain.Reviews;
import com.breeze.breezevideouser.domain.Users;
import com.breeze.breezevideouser.domain.dto.UsersLoginDto;
import com.breeze.breezevideouser.service.ReviewsService;
import com.breeze.breezevideouser.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author breeze
 * @date 2023/4/9 20:51
 */
@SpringBootTest
class UsersControllerTest {

    @Autowired
    private ReviewsService reviewsService;

    @Autowired
    private UsersService usersService;

    private AuthController authController = new AuthController();
    private final String[] MAILS = new String[]
            {"gmail.com", "outlook.com", "yahoo.com", "icloud.com", "gmail.com", "gmail.com"};
    private final String[] GENDERS = new String[]
            {"M", "F", "O"};
    @Test
//    @Transactional
    void createUsersByReviewsName() {
        QueryWrapper<Reviews> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("author");
        queryWrapper.select("author");
        List<Reviews> reviewsList = reviewsService.list(queryWrapper);
        for (Reviews reviews : reviewsList) {
            QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
            usersQueryWrapper.eq("username", reviews.getAuthor());
            if (!Objects.isNull(usersService.getOne(usersQueryWrapper))){
                continue;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(RandomUtil.randomString(RandomUtil.randomInt(6, 12)))
                    .append("@")
                    .append(MAILS[RandomUtil.randomInt(0, 6)]);
            UsersLoginDto usersLoginDto = new UsersLoginDto();
            usersLoginDto.setEmail(sb.toString());
            usersLoginDto.setPassword("123qqq");
            System.out.println("====================================================");
            System.out.println("====================================================");
            HttpRequest.post("http://breeze-video.com/auth/register")
                    .body(JSONUtil.toJsonStr(usersLoginDto)).execute().close();
            System.out.println("====================================================");
            System.out.println("====================================================");
//            HttpRequest.post("http://breeze-video.com/auth/register")
//                    .body(JSONUtil.toJsonStr(usersLoginDto)).execute().body();
            UpdateWrapper<Users> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("username", reviews.getAuthor());
            updateWrapper.set("nickname", reviews.getAuthor());
            updateWrapper.set("gender", GENDERS[RandomUtil.randomInt(0, 3)]);
            updateWrapper.eq("email", sb.toString());
            usersService.update(updateWrapper);
        }





    }
}