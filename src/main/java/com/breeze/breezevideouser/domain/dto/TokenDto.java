package com.breeze.breezevideouser.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author breeze
 * @date 2023/4/3 09:43
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String userid;
    private String accessToken;
    private String refreshToken;
}
