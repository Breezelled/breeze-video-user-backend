package com.breeze.breezevideouser.mapper;

import com.breeze.breezevideouser.domain.Info;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.breeze.breezevideouser.domain.vo.InfoVo;
import org.apache.ibatis.annotations.Mapper;

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
}
