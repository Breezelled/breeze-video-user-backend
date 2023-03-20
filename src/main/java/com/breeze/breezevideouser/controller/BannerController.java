package com.breeze.breezevideouser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.domain.dto.BannerDto;
import com.breeze.breezevideouser.domain.vo.InfoVo;
import com.breeze.breezevideouser.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breeze.breezevideouser.common.ApiResponse;
import com.breeze.breezevideouser.log.annotation.WebLog;
import com.breeze.breezevideouser.service.BannerService;
import com.breeze.breezevideouser.domain.Banner;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author breeze
 * @since 2023-03-20
 */
@RestController
@Api(tags = "Banner 前端控制器")
@RequestMapping("/banner")
public class BannerController {

    final Logger logger = LoggerFactory.getLogger(BannerController.class);
    @Autowired
    private BannerService bannerService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RedisUtil redisUtil;

    @WebLog(description = "添加Banner")
    @ApiOperation(value = "添加Banner")
    @PostMapping
    @CacheEvict(value = "banner", key = "'banner.json'")
    public ApiResponse save(@RequestBody BannerDto bannerDto) {
        Banner banner = new Banner();
        modelMapper.map(bannerDto, banner);
        int cnt = (int) bannerService.count();
        banner.setId(cnt + 1);
        return ApiResponse.ok(bannerService.save(banner));
    }

    @WebLog(description = "用id删除Banner")
    @ApiOperation(value = "用id删除Banner")
    @DeleteMapping("/{id}")
    @CacheEvict(value = "banner", key = "'banner.json'")
    public ApiResponse delete(@PathVariable Integer id) {
            return ApiResponse.ok(bannerService.removeById(id));
            }

    @WebLog(description = "返回所有Banner")
    @ApiOperation(value = "返回所有Banner")
    @GetMapping
    public ApiResponse findAll() {
        List<InfoVo> banners = bannerService.getAllBanner();
        return ApiResponse.ok(banners);

    }

    @WebLog(description = "用id查找Banner")
    @ApiOperation(value = "用id查找Banner")
    @GetMapping("/{id}")
    public ApiResponse findOne(@PathVariable Integer id) {
            return ApiResponse.ok(bannerService.getById(id));
            }

    @WebLog(description = "分页Banner")
    @ApiOperation(value = "分页Banner")
    @GetMapping("/page")
    public ApiResponse findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
            return ApiResponse.ok(bannerService.page(new Page<>(pageNum, pageSize)));
    }

}
