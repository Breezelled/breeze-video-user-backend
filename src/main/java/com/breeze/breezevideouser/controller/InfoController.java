package com.breeze.breezevideouser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.breezevideouser.domain.S3;
import com.breeze.breezevideouser.domain.vo.InfoVo;
import com.breeze.breezevideouser.domain.vo.UsersVo;
import com.breeze.breezevideouser.service.S3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breeze.breezevideouser.common.ApiResponse;
import com.breeze.breezevideouser.log.annotation.WebLog;
import com.breeze.breezevideouser.service.InfoService;
import com.breeze.breezevideouser.domain.Info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 电影信息 前端控制器
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
@RestController
@Api(tags = "电影信息 前端控制器")
@RequestMapping("/info")
public class InfoController {

    final Logger logger = LoggerFactory.getLogger(InfoController.class);
    @Autowired
    private InfoService infoService;

    @Autowired
    private S3Service s3Service;
    @WebLog(description = "添加电影信息")
    @ApiOperation(value = "添加电影信息")
    @PostMapping
    @CacheEvict(value = "topRated", key = "'topRated.json'")
    public ApiResponse save(@RequestBody Info info) {
            return ApiResponse.ok(infoService.save(info));
            }

    @WebLog(description = "用id删除电影信息")
    @ApiOperation(value = "用id删除电影信息")
    @DeleteMapping("/{id}")
    @CacheEvict(value = "topRated", key = "'topRated.json'")
    public ApiResponse delete(@PathVariable Integer id) {
            return ApiResponse.ok(infoService.removeById(id));
            }

    @WebLog(description = "查询全部电影信息")
    @ApiOperation(value = "查询全部电影信息")
    @GetMapping
    public ApiResponse findAll() {
            return ApiResponse.ok(infoService.list());
            }

    @WebLog(description = "用id查找电影信息")
    @ApiOperation(value = "用id查找电影信息")
    @GetMapping("/{id}")
    public ApiResponse findOne(@PathVariable Integer id) {
            return ApiResponse.ok(infoService.getById(id));
            }

    @WebLog(description = "分页电影信息")
    @ApiOperation(value = "分页电影信息")
    @GetMapping("/page")
    public ApiResponse findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
            return ApiResponse.ok(infoService.page(new Page<>(pageNum, pageSize)));
    }

    @WebLog(description = "电影评分最高的前x个")
    @ApiOperation(value = "电影评分最高的前x个")
    @GetMapping("/rating")
    public ApiResponse findRating(@RequestParam Integer topN) {
        QueryWrapper<Info> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("rating");
        queryWrapper.last("limit " + topN);
        return ApiResponse.ok(infoService.list(queryWrapper));
    }

    @WebLog(description = "随机选择n个")
    @ApiOperation(value = "随机选择n个")
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
    @ApiOperation(value = "分页电影信息模糊查询不区分大小写")
    @GetMapping("/pageByContentI")
    public ApiResponse findPageByContent(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         @RequestParam String content) {
        QueryWrapper<Info> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("where name ilike '%" + content + "%'");
        return ApiResponse.ok(infoService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @WebLog(description = "分页电影分类模糊查询")
    @ApiOperation(value = "分页电影分类模糊查询")
    @GetMapping("/pageByType")
    public ApiResponse findPageByType(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         @RequestParam String type) {
        QueryWrapper<Info> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("type", type);
        queryWrapper.orderByDesc("rating");
        return ApiResponse.ok(infoService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @WebLog(description = "最新发布的电影评分最高的前100个")
    @ApiOperation(value = "最新发布的电影评分最高的前100个")
    @GetMapping("/topRated")
    public ApiResponse topRated() {
        return ApiResponse.ok(infoService.topRated());
    }

    @WebLog(description = "查询评分最高的特定类型的影片，评分相同按评分数排名")
    @ApiOperation(value = "查询评分最高的特定类型的影片，评分相同按评分数排名")
    @GetMapping("/topRatedAndNumByType/{type}/{limit}")
    public ApiResponse topRatedAndNumByType(@PathVariable String type, @PathVariable Integer limit) {
        return ApiResponse.ok(infoService.topRatedAndNumByType(type, limit));
    }

    @WebLog(description = "查询type数最多的排名")
    @ApiOperation(value = "查询type数最多的排名")
    @GetMapping("/topNumType/{countLimit}")
    public ApiResponse topNumType(@PathVariable Integer countLimit) {
        return ApiResponse.ok(infoService.topNumType(countLimit));
    }

    @WebLog(description = "给用户个性化推荐")
    @ApiOperation(value = "给用户个性化推荐")
    @PostMapping("/recommend")
    public ApiResponse recommend(@RequestBody UsersVo usersVo,
                                 @RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize) {
        List<InfoVo> list = infoService.personalizedRecommendation(usersVo.getUserid());
        Map<String, Object> map = new ConcurrentHashMap<>();
        int total = list.size();
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, total);
        int pageCount = total / pageSize + (total % pageSize == 0 ? 0 : 1);
        List<InfoVo> pageList = list.subList(fromIndex, toIndex);
        map.put("records", pageList);
        map.put("total", total);
        map.put("size", pageSize);
        map.put("current", pageNum);
        map.put("page", pageCount);
        return ApiResponse.ok(map);
    }
}
