package com.breeze.breezevideouser.domain;

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
 * @since 2023-03-20
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "Banner对象", description = "Banner")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("banner id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("电影id")
    private Integer movieId;

    @ApiModelProperty("banner到期时间")
    private LocalDateTime expireTime;


}
