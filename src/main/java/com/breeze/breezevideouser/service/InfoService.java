package com.breeze.breezevideouser.service;

import com.breeze.breezevideouser.domain.Info;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.breezevideouser.domain.vo.InfoVo;
import com.breeze.breezevideouser.domain.vo.TypeVo;

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
     * 查询最新release的且rating>7.0 且都有海报和预告片的
     * @return 25个影片
     */
    List<InfoVo> topRated();

    /**
     * 查询评分最高的特定类型的影片，评分相同按评分数排名 且都有海报和预告片的
     * @param type 类型
     * @param limit 返回个数
     * @return 25个影片
     */
    public List<InfoVo> topRatedAndNumByType(String type, Integer limit);

    /**
     * 将每个电影type分割后按type数最多的排序
     * @param countLimit 最小count值
     * @return 所有的type个数信息
     */
    public List<TypeVo> topNumType(Integer countLimit);
}
