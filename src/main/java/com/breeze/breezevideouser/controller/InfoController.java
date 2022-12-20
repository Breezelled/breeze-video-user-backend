package com.breeze.breezevideouser.controller;

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
import com.breeze.breezevideouser.service.InfoService;
import com.breeze.breezevideouser.DO.Info;

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

}
