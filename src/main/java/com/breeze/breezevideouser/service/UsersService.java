package com.breeze.breezevideouser.service;

import com.breeze.breezevideouser.domain.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author breeze
 * @since 2023-03-14
 */
public interface UsersService extends IService<Users>, UserDetailsService {

}
