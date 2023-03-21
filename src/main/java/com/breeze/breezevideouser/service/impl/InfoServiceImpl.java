package com.breeze.breezevideouser.service.impl;

import com.breeze.breezevideouser.domain.Info;
import com.breeze.breezevideouser.domain.vo.InfoVo;
import com.breeze.breezevideouser.mapper.InfoMapper;
import com.breeze.breezevideouser.service.InfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 电影信息 服务实现类
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements InfoService {

    @Autowired
    private InfoMapper infoMapper;

    @Cacheable(value = "topRated", key = "'topRated.json'")
    @Override
    public List<InfoVo> topRated() {
        return infoMapper.topRated();
    }

    @Override
    public List<InfoVo> topRatedAndNumByType(String type) {
        return infoMapper.topRatedAndNumByType(type);
    }
}
