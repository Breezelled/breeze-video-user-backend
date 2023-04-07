package com.breeze.breezevideouser.domain.dto;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("用户uuid")
    private String userid;

    @ApiModelProperty("访问令牌")
    private String accessToken;

    @ApiModelProperty("刷新令牌")
    private String refreshToken;
}
