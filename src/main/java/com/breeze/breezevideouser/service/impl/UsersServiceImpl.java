package com.breeze.breezevideouser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.domain.Users;
import com.breeze.breezevideouser.mapper.UsersMapper;
import com.breeze.breezevideouser.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author breeze
 * @since 2023-03-14
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService, UserDetailsManager {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public void createUser(UserDetails user) {
        ((Users) user).setPassword(passwordEncoder.encode(user.getPassword()));
        ((Users) user).setUserid(UUID.randomUUID().toString());
        ((Users) user).setGender("O");
        ((Users) user).setCreateTime(LocalDateTime.now());
        usersMapper.insert((Users) user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String email) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return Objects.isNull(usersMapper.selectOne(queryWrapper));
    }

    /**
     * 实际上通过email
     * @param email
     * @return user
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return usersMapper.selectOne(queryWrapper);
    }
}
