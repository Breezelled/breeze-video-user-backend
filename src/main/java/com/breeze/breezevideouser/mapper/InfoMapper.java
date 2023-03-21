package com.breeze.breezevideouser.mapper;

import com.breeze.breezevideouser.domain.Info;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.breeze.breezevideouser.domain.vo.InfoVo;
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
     * 查询最新release的且rating>7.0
     * @return 100个影片
     */
    List<InfoVo> topRated();

    /**
     * 查询评分最高的特定类型的影片，评分相同按评分数排名
     * @param type 类型
     * @return 100个影片
     */
    List<InfoVo> topRatedAndNumByType(@Param("type") String type);

}
