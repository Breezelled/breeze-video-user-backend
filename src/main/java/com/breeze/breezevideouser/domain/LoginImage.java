package com.breeze.breezevideouser.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录页面图像
 * </p>
 *
 * @author breeze
 * @since 2023-03-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("login_image")
@ApiModel(value = "LoginImage对象", description = "登录页面图像")
public class LoginImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("登录页面图像id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("是否是当前选择")
    private Boolean currentChoice;


}
