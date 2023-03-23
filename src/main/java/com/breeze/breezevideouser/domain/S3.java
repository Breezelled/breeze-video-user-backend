package com.breeze.breezevideouser.domain;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * S3
 * </p>
 *
 * @author breeze
 * @since 2022-12-22
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "S3对象", description = "")
public class S3 implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("桶名称")
    private String bucketName;

    @ApiModelProperty("对象名称")
    private String objectName;

    @ApiModelProperty("电影id")
    private Integer movieId;

    @ApiModelProperty("登录页面图像id")
    private Integer loginImageId;

}
