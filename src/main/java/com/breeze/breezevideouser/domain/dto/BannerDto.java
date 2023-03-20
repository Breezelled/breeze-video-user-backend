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
 *
 * </p>
 *
 * @author breeze
 * @since 2023-03-20
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "BannerDto对象", description = "BannerDto")
public class BannerDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("电影id")
    private Integer movieId;

    @ApiModelProperty("banner到期时间")
    private LocalDateTime expireTime;

}
