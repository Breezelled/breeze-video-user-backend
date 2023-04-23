package com.breeze.breezevideouser.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.breeze.breezevideouser.domain.Info;
import com.breeze.breezevideouser.domain.Reviews;
import com.breeze.breezevideouser.domain.Users;
import com.breeze.breezevideouser.domain.dto.UsersLoginDto;
import com.breeze.breezevideouser.service.InfoService;
import com.breeze.breezevideouser.service.ReviewsService;
import com.breeze.breezevideouser.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
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

    @Autowired
    private InfoService infoService;

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

    @Test
    void createUserInterestType() {
        QueryWrapper<Reviews> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("author");
        queryWrapper.select("author");
        List<Reviews> reviewsList = reviewsService.list(queryWrapper);
        for (Reviews reviews : reviewsList) {
            QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
            usersQueryWrapper.eq("username", reviews.getAuthor());
            Users users = usersService.getOne(usersQueryWrapper);
            if (Objects.isNull(users)){
                continue;
            }
            QueryWrapper<Reviews> reviewsQueryWrapper = new QueryWrapper<>();
            reviewsQueryWrapper.eq("author", users.getUsername());
            List<Reviews> list = reviewsService.list(reviewsQueryWrapper);
            HashSet<String> set = new HashSet<>();
            for (Reviews r: list) {
                QueryWrapper<Info> infoQueryWrapper = new QueryWrapper<>();
                infoQueryWrapper.eq("id", r.getMovieId());
                Info info = infoService.getOne(infoQueryWrapper);
                if (Objects.isNull(info.getType())) {
                    continue;
                }
                String[] split = info.getType().split("\\|");
                set.addAll(Arrays.asList(split));
            }
            StringBuilder sb = new StringBuilder();
            int cnt = 0;
            for (String s:set) {
                sb.append(s);
                sb.append("|");
                cnt++;
                if (cnt == 6){
                    break;
                }
            }
            if (cnt < 3){
                UpdateWrapper<Users> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("interest_type", null);
                updateWrapper.eq("id", users.getId());
                usersService.update(updateWrapper);
                continue;
            }
            if (sb.length() == 0){
                continue;
            }
            sb.deleteCharAt(sb.length()-1);
            UpdateWrapper<Users> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("interest_type", sb.toString());
            updateWrapper.eq("id", users.getId());
            usersService.update(updateWrapper);
            System.out.println("====================================================");
            System.out.println("====================================================");
            System.out.println("=============================" + sb.toString() +"==" + users.getId());
            System.out.println("====================================================");
            System.out.println("====================================================");
        }
    }
}