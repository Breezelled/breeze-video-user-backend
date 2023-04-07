package com.breeze.breezevideouser.domain.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户VO
 * </p>
 *
 * @author breeze
 * @since 2023-03-14
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "UsersVo对象", description = "用户Vo")
public class UsersVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("自定义用户名")
    private String nickname;

    @ApiModelProperty("介绍")
    private String intro;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("注册时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后登录区域")
    private String lastLoginArea;

    @ApiModelProperty("头像地址")
    private String avatar;

    @ApiModelProperty("兴趣类型")
    private String interestType;

    @ApiModelProperty("用户uuid")
    private String userid;

    @ApiModelProperty("访问令牌")
    private String accessToken;

    @ApiModelProperty("刷新令牌")
    private String refreshToken;
}
