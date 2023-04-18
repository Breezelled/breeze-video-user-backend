package com.breeze.breezevideouser.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breeze.breezevideouser.common.ApiResponse;
import com.breeze.breezevideouser.log.annotation.WebLog;
import com.breeze.breezevideouser.service.ReviewsService;
import com.breeze.breezevideouser.domain.Reviews;

/**
 * <p>
 * 电影评论 前端控制器
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
@RestController
@Api(tags = "电影评论 前端控制器")
@RequestMapping("/reviews")
public class ReviewsController {

    final Logger logger = LoggerFactory.getLogger(ReviewsController.class);
    @Autowired
    private ReviewsService reviewsService;

    @WebLog(description = "添加电影评论")
    @ApiOperation(value = "添加电影评论")
    @PostMapping
    public ApiResponse save(@RequestBody Reviews reviews) {
        reviews.setSentimentRating(reviewsService.sentimentAnalysis(reviews.getContent()));
        return ApiResponse.ok(reviewsService.save(reviews));
    }

    @WebLog(description = "用id删除电影评论")
    @ApiOperation(value = "用id删除电影评论")
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
            return ApiResponse.ok(reviewsService.removeById(id));
            }

    @WebLog(description = "查询全部电影评论")
    @ApiOperation(value = "查询全部电影评论")
    @GetMapping
    public ApiResponse findAll() {
            return ApiResponse.ok(reviewsService.list());
            }

    @WebLog(description = "用id查找电影评论")
    @ApiOperation(value = "用id查找电影评论")
    @GetMapping("/{id}")
    public ApiResponse findOne(@PathVariable Integer id) {
            return ApiResponse.ok(reviewsService.getById(id));
            }

    @WebLog(description = "分页电影评论")
    @ApiOperation(value = "分页电影评论")
    @GetMapping("/page")
    public ApiResponse findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
            return ApiResponse.ok(reviewsService.page(new Page<>(pageNum, pageSize)));
    }

}
