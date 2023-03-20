package com.breeze.breezevideouser.domain;

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
@ApiModel(value = "Favorites对象", description = "收藏对象")
public class Favorites implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("收藏id")
    private Integer id;

    @ApiModelProperty("电影id")
    private Integer movieId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("收藏的时间")
    private LocalDateTime favoriteTime;


}
