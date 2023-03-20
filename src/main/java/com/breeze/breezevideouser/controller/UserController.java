package com.breeze.breezevideouser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.domain.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
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
import com.breeze.breezevideouser.service.UserService;
import com.breeze.breezevideouser.domain.User;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author breeze
 * @since 2023-03-14
 */
@RestController
@Api(tags = "用户 前端控制器")
@RequestMapping("/user")
public class UserController {

    final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @WebLog(description = "添加用户")
    @ApiOperation(value = "添加用户")
    @PostMapping
    public ApiResponse save(@RequestBody User user) {
            return ApiResponse.ok(userService.saveOrUpdate(user));
            }

    @WebLog(description = "用id删除用户")
    @ApiOperation(value = "用id删除用户")
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
            return ApiResponse.ok(userService.removeById(id));
            }

    @WebLog(description = "查询全部用户")
    @ApiOperation(value = "查询全部用户")
    @GetMapping
    public ApiResponse findAll() {
            return ApiResponse.ok(userService.list());
            }

    @WebLog(description = "用id查找用户")
    @ApiOperation(value = "用id查找用户")
    @GetMapping("/{id}")
    public ApiResponse findOne(@PathVariable Integer id) {
            return ApiResponse.ok(userService.getById(id));
            }

    @WebLog(description = "分页用户")
    @ApiOperation(value = "分页用户")
    @GetMapping("/page")
    public ApiResponse findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
            return ApiResponse.ok(userService.page(new Page<>(pageNum, pageSize)));
    }
    
}
