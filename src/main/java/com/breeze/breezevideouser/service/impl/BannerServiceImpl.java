package com.breeze.breezevideouser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.domain.Banner;
import com.breeze.breezevideouser.domain.Info;
import com.breeze.breezevideouser.domain.S3;
import com.breeze.breezevideouser.domain.vo.InfoVo;
import com.breeze.breezevideouser.mapper.BannerMapper;
import com.breeze.breezevideouser.mapper.InfoMapper;
import com.breeze.breezevideouser.mapper.S3Mapper;
import com.breeze.breezevideouser.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author breeze
 * @since 2023-03-20
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Autowired
    private InfoMapper infoMapper;

    @Cacheable(value = "banner", key = "'banner.json'")
    @Override
    public List<InfoVo> getAllBanner() {
        return infoMapper.getAllBanner();
    }
}
