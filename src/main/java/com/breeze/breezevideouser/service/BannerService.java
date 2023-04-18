package com.breeze.breezevideouser.service;

import com.breeze.breezevideouser.domain.Banner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.breezevideouser.domain.vo.InfoVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author breeze
 * @since 2023-03-20
 */
public interface BannerService extends IService<Banner> {
    /**
     * 获取所有Banner
     * @return 所有的Banner
     */
    List<InfoVo> getAllBanner();
}
