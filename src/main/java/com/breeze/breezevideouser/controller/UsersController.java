package com.breeze.breezevideouser.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.domain.dto.UsersLoginDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breeze.breezevideouser.common.ApiResponse;
import com.breeze.breezevideouser.log.annotation.WebLog;
import com.breeze.breezevideouser.service.UsersService;
import com.breeze.breezevideouser.domain.Users;

import java.time.LocalDateTime;

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
public class UsersController {

    final Logger logger = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    private UsersService usersService;

    @Autowired
    private ModelMapper modelMapper;

    @WebLog(description = "注册")
    @ApiOperation(value = "注册")
    @PostMapping
    public ApiResponse save(@RequestBody UsersLoginDto userLoginDto) {
        Users user = modelMapper.map(userLoginDto, Users.class);
        String email = user.getEmail();
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);

        if (!ObjectUtil.isNull(usersService.getOne(queryWrapper))) {
            return ApiResponse.error(406, "This email is used already.");
        }

        String secretPwd = SecureUtil.sha256(user.getPassword());
        user.setPassword(secretPwd);
        user.setCreateTime(LocalDateTime.now());
        return ApiResponse.ok(200, "Sign up success!", usersService.save(user));
    }

    /**
     * TODO not finish
     */
    @WebLog(description = "登录")
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ApiResponse login(@RequestBody UsersLoginDto userLoginDto) {
        Users user = modelMapper.map(userLoginDto, Users.class);
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", user.getEmail()).eq("password", SecureUtil.sha256(user.getPassword()));

        if (!ObjectUtil.isNull(usersService.getOne(queryWrapper))) {
            return ApiResponse.error(404, "Received wrong email or password.");
        }

        user.setLastLoginTime(LocalDateTime.now());
        return ApiResponse.ok(200, "Sign in success!", usersService.save(user));
    }

    @WebLog(description = "用id删除用户")
    @ApiOperation(value = "用id删除用户")
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
            return ApiResponse.ok(usersService.removeById(id));
            }

    @WebLog(description = "查询全部用户")
    @ApiOperation(value = "查询全部用户")
    @GetMapping
    public ApiResponse findAll() {
            return ApiResponse.ok(usersService.list());
            }

    @WebLog(description = "用id查找用户")
    @ApiOperation(value = "用id查找用户")
    @GetMapping("/{id}")
    public ApiResponse findOne(@PathVariable Integer id) {
            return ApiResponse.ok(usersService.getById(id));
            }

    @WebLog(description = "分页用户")
    @ApiOperation(value = "分页用户")
    @GetMapping("/page")
    public ApiResponse findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
            return ApiResponse.ok(usersService.page(new Page<>(pageNum, pageSize)));
    }
    
}
