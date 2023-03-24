package com.breeze.breezevideouser.controller;

import com.breeze.breezevideouser.mapper.LoginImageMapper;
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
import com.breeze.breezevideouser.service.LoginImageService;
import com.breeze.breezevideouser.domain.LoginImage;

/**
 * <p>
 * 登录页面图像 前端控制器
 * </p>
 *
 * @author breeze
 * @since 2023-03-22
 */
@RestController
@Api(tags = "登录页面图像 前端控制器")
@RequestMapping("/loginImage")
public class LoginImageController {

    final Logger logger = LoggerFactory.getLogger(LoginImageController.class);
    @Autowired
    private LoginImageService loginImageService;

    @Autowired
    private LoginImageMapper loginImageMapper;

    @WebLog(description = "添加登录页面图像")
    @ApiOperation(value = "添加登录页面图像")
    @PostMapping
    public ApiResponse save(@RequestBody LoginImage loginImage) {
            return ApiResponse.ok(loginImageService.save(loginImage));
    }

    @WebLog(description = "用id删除登录页面图像")
    @ApiOperation(value = "用id删除登录页面图像")
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
            return ApiResponse.ok(loginImageService.removeById(id));
            }

    @WebLog(description = "查询全部登录页面图像")
    @ApiOperation(value = "查询全部登录页面图像")
    @GetMapping
    public ApiResponse findAll() {
            return ApiResponse.ok(loginImageService.list());
            }

    @WebLog(description = "用id查找登录页面图像")
    @ApiOperation(value = "用id查找登录页面图像")
    @GetMapping("/{id}")
    public ApiResponse findOne(@PathVariable Integer id) {
            return ApiResponse.ok(loginImageService.getById(id));
            }

    @WebLog(description = "分页登录页面图像")
    @ApiOperation(value = "分页登录页面图像")
    @GetMapping("/page")
    public ApiResponse findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
            return ApiResponse.ok(loginImageService.page(new Page<>(pageNum, pageSize)));
    }

    @WebLog(description = "用id查找登录页面图像")
    @ApiOperation(value = "用id查找登录页面图像")
    @PutMapping()
    public ApiResponse updateOne(@RequestBody LoginImage loginImage) {
        return ApiResponse.ok(loginImageService.updateById(loginImage));
    }

    @WebLog(description = "查找当前管理员选择的登录页面图像")
    @ApiOperation(value = "查找当前管理员选择的登录页面图像")
    @GetMapping("/login")
    public ApiResponse updateOne() {
        return ApiResponse.ok(loginImageMapper.getCurrentLoginImage());
    }

}
