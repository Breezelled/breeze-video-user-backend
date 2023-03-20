package com.breeze.breezevideouser.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
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

    @WebLog(description = "添加收藏")
    @ApiOperation(value = "添加收藏")
    @PostMapping
    public ApiResponse save(@RequestBody Favorites favorites) {
            return ApiResponse.ok(favoritesService.saveOrUpdate(favorites));
            }

    @WebLog(description = "用id删除收藏")
    @ApiOperation(value = "用id删除收藏")
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
            return ApiResponse.ok(favoritesService.removeById(id));
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

}
