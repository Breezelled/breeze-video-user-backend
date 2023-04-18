package com.breeze.breezevideouser.service;

import com.breeze.breezevideouser.domain.Reviews;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 电影评论 服务类
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
public interface ReviewsService extends IService<Reviews> {
    /**
     * 评论情感分析
     * @param content 评论内容
     * @return 分析后的数值
     */
    Double sentimentAnalysis(String content);
}
