package com.breeze.breezevideouser.mapper;

import com.breeze.breezevideouser.domain.LoginImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 登录页面图像 Mapper 接口
 * </p>
 *
 * @author breeze
 * @since 2023-03-22
 */
public interface LoginImageMapper extends BaseMapper<LoginImage> {

    /**
     * 获取当前管理员选择的登录页面图片
     * @return 图片链接
     */
    String getCurrentLoginImage();

}
