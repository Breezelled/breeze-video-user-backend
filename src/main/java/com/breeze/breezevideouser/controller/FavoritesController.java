package com.breeze.breezevideouser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.domain.dto.FavoritesLikesDto;
import com.breeze.breezevideouser.domain.vo.InfoVo;
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
import com.breeze.breezevideouser.service.FavoritesService;
import com.breeze.breezevideouser.domain.Favorites;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author breeze
 * @since 2023-03-20
 */
@RestController
@Api(tags = "收藏 前端控制器")
@RequestMapping("/favorites")
public class FavoritesController {

    final Logger logger = LoggerFactory.getLogger(FavoritesController.class);
    @Autowired
    private FavoritesService favoritesService;

    @Autowired
    private InfoService infoService;

    @WebLog(description = "添加收藏")
    @ApiOperation(value = "添加收藏")
    @PostMapping
    public ApiResponse save(@RequestBody Favorites favorites) {
        favorites.setFavoriteTime(LocalDateTime.now());
        return ApiResponse.ok(favoritesService.save(favorites));
    }

    @WebLog(description = "用id删除收藏")
    @ApiOperation(value = "用id删除收藏")
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
            return ApiResponse.ok(favoritesService.removeById(id));
            }

    @WebLog(description = "用id删除收藏")
    @ApiOperation(value = "用id删除收藏")
    @DeleteMapping()
    public ApiResponse deleteByUser(@RequestBody FavoritesLikesDto favoritesLikesDto) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("movie_id", favoritesLikesDto.getMovieId());
        map.put("user_id", favoritesLikesDto.getUserId());
        return ApiResponse.ok(favoritesService.removeByMap(map));
    }

    @WebLog(description = "查询全部收藏")
    @ApiOperation(value = "查询全部收藏")
    @GetMapping
    public ApiResponse findAll() {
            return ApiResponse.ok(favoritesService.list());
            }

    @WebLog(description = "用id查找收藏")
    @ApiOperation(value = "用id查找收藏")
    @GetMapping("/{id}")
    public ApiResponse findOne(@PathVariable Integer id) {
            return ApiResponse.ok(favoritesService.getById(id));
            }

    @WebLog(description = "分页收藏")
    @ApiOperation(value = "分页收藏")
    @GetMapping("/page")
    public ApiResponse findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        return ApiResponse.ok(favoritesService.page(new Page<>(pageNum, pageSize)));
    }

    @WebLog(description = "根据用户查找其收藏并分页")
    @ApiOperation(value = "根据用户查找其收藏并分页")
    @PostMapping("/user")
    public ApiResponse userFavorites(@RequestBody FavoritesLikesDto favoritesLikesDto,
                                     @RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize) {
        return ApiResponse.ok(infoService.userFavorites(favoritesLikesDto.getUserId(), pageNum, pageSize));
    }

}
