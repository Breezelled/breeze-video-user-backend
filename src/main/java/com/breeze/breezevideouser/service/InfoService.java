package com.breeze.breezevideouser.service;

import com.breeze.breezevideouser.domain.Info;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.breezevideouser.domain.vo.InfoVo;

import java.util.List;

/**
 * <p>
 * 电影信息 服务类
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
public interface InfoService extends IService<Info> {
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
    public List<InfoVo> topRatedAndNumByType(String type);
}
