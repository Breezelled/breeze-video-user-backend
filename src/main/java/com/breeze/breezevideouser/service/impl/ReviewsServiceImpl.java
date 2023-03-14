package com.breeze.breezevideouser.service.impl;

import com.breeze.breezevideouser.domain.Reviews;
import com.breeze.breezevideouser.mapper.ReviewsMapper;
import com.breeze.breezevideouser.service.ReviewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 电影评论 服务实现类
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
@Service
public class ReviewsServiceImpl extends ServiceImpl<ReviewsMapper, Reviews> implements ReviewsService {

}
