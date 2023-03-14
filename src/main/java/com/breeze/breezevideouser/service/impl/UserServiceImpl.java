package com.breeze.breezevideouser.service.impl;

import com.breeze.breezevideouser.domain.User;
import com.breeze.breezevideouser.mapper.UserMapper;
import com.breeze.breezevideouser.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author breeze
 * @since 2023-03-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
