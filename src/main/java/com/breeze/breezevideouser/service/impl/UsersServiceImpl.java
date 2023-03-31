package com.breeze.breezevideouser.service.impl;

import com.breeze.breezevideouser.domain.Users;
import com.breeze.breezevideouser.mapper.UsersMapper;
import com.breeze.breezevideouser.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return null;
//    }
}
