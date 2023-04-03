package com.breeze.breezevideouser.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.breeze.breezevideouser.domain.Users;
import com.breeze.breezevideouser.domain.dto.TokenDto;
import com.breeze.breezevideouser.domain.dto.UsersLoginDto;
import com.breeze.breezevideouser.log.annotation.WebLog;
import com.breeze.breezevideouser.security.TokenGenerator;
import com.breeze.breezevideouser.service.UsersService;
import com.breeze.breezevideouser.utils.IPUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * <p>
 * 认证 前端控制器
 * </p>
 *
 * @author breeze
 * @since 2023-03-14
 */
@RestController
@Api(tags = "认证 前端控制器")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserDetailsManager userDetailsManager;
    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;
    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    JwtAuthenticationProvider refreshTokenAuthProvider;
    @Autowired
    private Searcher searcher;
    @Autowired
    private UsersService usersService;

    @WebLog(description = "注册")
    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UsersLoginDto usersLoginDto) {
        Users user = new Users(usersLoginDto.getPassword(), usersLoginDto.getEmail());
        userDetailsManager.createUser(user);

        Authentication authentication = UsernamePasswordAuthenticationToken
                .authenticated(user, usersLoginDto.getPassword(), Collections.EMPTY_LIST);

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @WebLog(description = "登录")
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UsersLoginDto usersLoginDto, HttpServletRequest request) {
        Authentication authentication = daoAuthenticationProvider
                .authenticate(UsernamePasswordAuthenticationToken
                        .unauthenticated(usersLoginDto.getEmail(), usersLoginDto.getPassword()));

        Users users = (Users) authentication.getPrincipal();

        String ip = IPUtil.getIp(request);
        users.setLastLoginIp(ip);
        try {
            if (searcher != null) {
                String region = searcher.search(ip);
                if (StringUtils.isNotEmpty(region)) {
                    users.setLastLoginArea(region);
                }
            }
        } catch (Exception ignored) {

        }
        users.setLastLoginTime(LocalDateTime.now());
        usersService.updateById(users);

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @WebLog(description = "获取新的accessToken")
    @ApiOperation(value = "获取新的accessToken")
    @PostMapping("/token")
    public ResponseEntity token(@RequestBody TokenDto tokenDto) {
        Authentication authentication = refreshTokenAuthProvider.authenticate(
                new BearerTokenAuthenticationToken(tokenDto.getRefreshToken()));
        Jwt jwt = (Jwt) authentication.getCredentials();
        // check if present in db and not revoked, etc

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }
}
