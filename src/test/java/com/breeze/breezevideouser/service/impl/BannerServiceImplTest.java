package com.breeze.breezevideouser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.domain.Banner;
import com.breeze.breezevideouser.domain.Info;
import com.breeze.breezevideouser.domain.vo.InfoVo;
import com.breeze.breezevideouser.mapper.BannerMapper;
import com.breeze.breezevideouser.mapper.InfoMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author breeze
 * @date 2023/3/20 17:30
 */
@SpringBootTest
class BannerServiceImplTest {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private InfoMapper infoMapper;

    @Test
    void getAllBanner() {
        QueryWrapper<Banner> queryWrapper = new QueryWrapper<>();
        List<Banner> banners = bannerMapper.selectList(queryWrapper);
        List<Integer> ids = banners.stream().map(Banner::getId).toList();
        List<Info> infos = infoMapper.selectBatchIds(ids);
        List<InfoVo> infoVos = modelMapper.map(infos, new TypeToken<List<InfoVo>>(){}.getType());
        System.out.println(infoVos);
        System.out.println(infos);
    }
}