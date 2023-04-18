package com.breeze.breezevideouser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.domain.dto.FavoritesLikesDto;
import com.breeze.breezevideouser.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breeze.breezevideouser.common.ApiResponse;
import com.breeze.breezevideouser.log.annotation.WebLog;
import com.breeze.breezevideouser.service.LikesService;
import com.breeze.breezevideouser.domain.Likes;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author breeze
 * @since 2023-04-18
 */
@RestController
@Api(tags = " 前端控制器")
@RequestMapping("/likes")
public class LikesController {

    final Logger logger = LoggerFactory.getLogger(LikesController.class);
    @Autowired
    private LikesService likesService;

    @Autowired
    private InfoService infoService;

    @WebLog(description = "添加")
    @ApiOperation(value = "添加")
    @PostMapping
    public ApiResponse save(@RequestBody Likes likes) {
        likes.setLikeTime(LocalDateTime.now());
        return ApiResponse.ok(likesService.save(likes));
    }

    @WebLog(description = "用id删除")
    @ApiOperation(value = "用id删除")
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
            return ApiResponse.ok(likesService.removeById(id));
            }

    @WebLog(description = "查询全部")
    @ApiOperation(value = "查询全部")
    @GetMapping
    public ApiResponse findAll() {
            return ApiResponse.ok(likesService.list());
            }

    @WebLog(description = "用id查找")
    @ApiOperation(value = "用id查找")
    @GetMapping("/{id}")
    public ApiResponse findOne(@PathVariable Integer id) {
            return ApiResponse.ok(likesService.getById(id));
            }

    @WebLog(description = "分页")
    @ApiOperation(value = "分页")
    @GetMapping("/page")
    public ApiResponse findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
            return ApiResponse.ok(likesService.page(new Page<>(pageNum, pageSize)));
    }

    @WebLog(description = "用id删除点赞")
    @ApiOperation(value = "用id删除点赞")
    @DeleteMapping()
    public ApiResponse deleteByUser(@RequestBody FavoritesLikesDto favoritesLikesDto) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("movie_id", favoritesLikesDto.getMovieId());
        map.put("user_id", favoritesLikesDto.getUserId());
        return ApiResponse.ok(likesService.removeByMap(map));
    }

    @WebLog(description = "根据用户查找其点赞")
    @ApiOperation(value = "根据用户查找其点赞")
    @PostMapping("/user")
    public ApiResponse userLikes(@RequestBody FavoritesLikesDto favoritesLikesDto) {
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", favoritesLikesDto.getUserId());
        queryWrapper.orderByDesc("like_time");
        logger.info("hello " + queryWrapper.getTargetSql());
        logger.info("hello " + favoritesLikesDto.getUserId());
        List<Likes> list = likesService.list(queryWrapper);
        List<Integer> ids = new ArrayList<>();
        for (Likes l: list) {
            ids.add(l.getMovieId());
        }
        if (!ids.isEmpty()){
            return ApiResponse.ok(infoService.listByIds(ids));
        }
        return ApiResponse.ok(new ArrayList<>());
    }
}
