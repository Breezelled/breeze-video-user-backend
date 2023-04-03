package com.breeze.breezevideouser.security;

import com.breeze.breezevideouser.domain.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author breeze
 * @date 2023/4/1 20:41
 */
@Component
public class JwtToUsersConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    final Logger logger = LoggerFactory.getLogger(JwtToUsersConverter.class);

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {
        Users users = new Users();
        users.setUserid(source.getSubject());
        return new UsernamePasswordAuthenticationToken(users, source, Collections.EMPTY_LIST);
    }
}
