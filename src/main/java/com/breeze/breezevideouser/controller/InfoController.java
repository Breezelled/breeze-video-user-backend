package com.breeze.breezevideouser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.DO.S3;
import com.breeze.breezevideouser.service.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breeze.breezevideouser.common.ApiResponse;
import com.breeze.breezevideouser.log.annotation.WebLog;
import com.breeze.breezevideouser.service.InfoService;
import com.breeze.breezevideouser.DO.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 电影信息 前端控制器
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
@RestController
@RequestMapping("/info")
public class InfoController {

    final Logger logger = LoggerFactory.getLogger(InfoController.class);
    @Autowired
    private InfoService infoService;

    @Autowired
    private S3Service s3Service;
    @WebLog(description = "添加电影信息")
    @PostMapping
    public ApiResponse save(@RequestBody Info info) {
            return ApiResponse.ok(infoService.saveOrUpdate(info));
            }

    @WebLog(description = "用id删除电影信息")
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
            return ApiResponse.ok(infoService.removeById(id));
            }

    @WebLog(description = "查询全部电影信息")
    @GetMapping
    public ApiResponse findAll() {
            return ApiResponse.ok(infoService.list());
            }

    @WebLog(description = "用id查找电影信息")
    @GetMapping("/{id}")
    public ApiResponse findOne(@PathVariable Integer id) {
            return ApiResponse.ok(infoService.getById(id));
            }

    @WebLog(description = "分页电影信息")
    @GetMapping("/page")
    public ApiResponse findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
            return ApiResponse.ok(infoService.page(new Page<>(pageNum, pageSize)));
    }

    @WebLog(description = "电影评分最高的前x个")
    @GetMapping("/rating")
    public ApiResponse findPage(@RequestParam Integer topN) {
        QueryWrapper<Info> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("rating");
        queryWrapper.last("limit " + topN);
        return ApiResponse.ok(infoService.list(queryWrapper));
    }

    @WebLog(description = "随机选择n个")
    @GetMapping("/random")
    public ApiResponse findRandom(@RequestParam Integer n) {
        QueryWrapper<S3> queryWrapper = new QueryWrapper<>();

        queryWrapper.last("ORDER BY RANDOM() limit " + n);

        List<S3> s3List = s3Service.list(queryWrapper);
        List<Integer> idList = new ArrayList<>();
        for (S3 s:s3List) {
            idList.add(s.getMovieId());
        }
        return ApiResponse.ok(infoService.listByIds(idList));
    }

    @WebLog(description = "分页电影信息模糊查询不区分大小写")
    @GetMapping("/pageByContentI")
    public ApiResponse findPageByContent(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         @RequestParam String content) {
        QueryWrapper<Info> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("where name ilike '%" + content + "%'");
        return ApiResponse.ok(infoService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @WebLog(description = "分页电影分类模糊查询")
    @GetMapping("/pageByType")
    public ApiResponse findPageByType(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         @RequestParam String type) {
        QueryWrapper<Info> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("type", type);
        queryWrapper.orderByDesc("rating");
        return ApiResponse.ok(infoService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}
