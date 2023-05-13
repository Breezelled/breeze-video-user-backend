package com.breeze.breezevideouser.mapper;

import com.breeze.breezevideouser.domain.Info;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.breeze.breezevideouser.domain.vo.InfoVo;
import com.breeze.breezevideouser.domain.vo.TypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 电影信息 Mapper 接口
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
@Mapper
public interface InfoMapper extends BaseMapper<Info> {
    /**
     * 多表查询
     * @return 返回所有Banner
     */
    List<InfoVo> getAllBanner();

    /**
     * 查询最新release的且rating>7.0 且都有海报和预告片的
     * @param offset 起始行
     * @param pageSize 当前页面数量
     * @return 25个影片
     */
    List<InfoVo> topRated(Integer offset, Integer pageSize);

    /**
     * 查询评分最高的特定类型的影片，评分相同按评分数排名 且都有海报和预告片的
     * @param type 类型
     * @param limit 返回个数
     * @return 25个影片
     */
    List<InfoVo> topRatedAndNumByType(@Param("type") String type, @Param("limit") Integer limit);

    /**
     * 将每个电影type分割后按type数最多的排序
     * @param countLimit 最小count值
     * @return 所有的type个数信息
     */
    List<TypeVo> topNumType(@Param("countLimit") Integer countLimit);

    /**
     * 查询用户收藏 按最近收藏优先返回
     * @param userId 用户uuid
     * @return 用户收藏列表
     */
    List<InfoVo> userFavorites(@Param("userId") String userId, Integer offset, Integer pageSize);

    /**
     * 查询所有包括在类型数组中的影片
     * @param types 用户兴趣类型数组
     * @return 所有包括在类型数组中的影片
     */
    List<InfoVo> getInfoByTypes(String[] types);

    /**
     * 查询分页带url的影片信息
     * @param offset 起始行
     * @param pageSize 当前页面数量
     * @return 分页结果
     */
    List<InfoVo> getPageInfo(Integer offset, Integer pageSize);

    /**
     * 查询海报和预告片都有的影片总数
     * @return 总数
     */
    int countBothUrlNum();

    /**
     * 查询最新release的且rating>7.0 且都有海报和预告片的总数
     * @return 总数
     */
    int countTopRated();

    /**
     * 查询最新release的且rating>7.0 且都有海报和预告片的总数
     * @param userId 用户uuid
     * @return 总数
     */
    int countUserFavorites(String userId);

    /**
     * 根据搜索内容进行模糊匹配后分页
     * @param offset 起始行
     * @param pageSize 当前页面数量
     * @param content 搜索内容
     * @return 分页结果
     */
    List<InfoVo> getPageByWebSearch(Integer offset, Integer pageSize, String content);

    /**
     * 查询搜索内容进行模糊匹配总数
     * @param content 搜索内容
     * @return 总数
     */
    int countWebSearch(String content);

}
