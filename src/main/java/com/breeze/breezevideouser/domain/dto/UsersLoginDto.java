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
@ApiModel(value = "UsersLoginDto对象", description = "用户注册登录Dto")
public class UsersLoginDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

}
