package com.breeze.breezevideouser.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户DTO
 * </p>
 *
 * @author breeze
 * @since 2023-03-14
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "UserDto对象", description = "用户Dto")
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

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

    @ApiModelProperty("头像地址")
    private String avatar;

    @ApiModelProperty("兴趣类型")
    private String interestType;


}
