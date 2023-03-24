package com.breeze.breezevideouser.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.breeze.breezevideouser.domain.dto.UsersLoginDto;
import com.breeze.breezevideouser.utils.IPUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lionsoul.ip2region.xdb.Searcher;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @Autowired
    private Searcher searcher;

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

    @WebLog(description = "登录")
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ApiResponse login(@RequestBody UsersLoginDto userLoginDto, HttpServletRequest request) {
        Users user = modelMapper.map(userLoginDto, Users.class);
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", user.getEmail()).eq("password", SecureUtil.sha256(user.getPassword()));

        Users users = usersService.getOne(queryWrapper);
        if (ObjectUtil.isNull(users)) {
            return ApiResponse.error(404, "Received wrong email or password.");
        }

        UpdateWrapper<Users> updateWrapper = new UpdateWrapper<>();

        String ip = IPUtil.getIp(request);
        updateWrapper.set("last_login_ip", ip);
        try {
            if (searcher != null) {
                String region = searcher.search(ip);
                if (StringUtils.isNotEmpty(region)) {
                    updateWrapper.set("last_login_area", region);
                }
            }
        } catch (Exception ignored) {

        }
        updateWrapper.setEntity(users);
        updateWrapper.set("last_login_time", LocalDateTime.now());

        return ApiResponse.ok(200, "Sign in success!", usersService.update(updateWrapper));
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
