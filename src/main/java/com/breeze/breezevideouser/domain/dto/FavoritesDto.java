package com.breeze.breezevideouser.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-04-18
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "FavoritesDto对象", description = "收藏Dto对象")
public class FavoritesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("收藏id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("电影id")
    private Integer movieId;

    @ApiModelProperty("用户uuid")
    private String userId;

}
