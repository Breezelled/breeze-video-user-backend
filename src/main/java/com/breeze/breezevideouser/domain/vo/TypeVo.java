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
 * 影片类型VO
 * </p>
 *
 * @author breeze
 * @since 2023-03-14
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "TypeVo对象", description = "电影类型Vo")
public class TypeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("电影名")
    private String name;

    @ApiModelProperty("类型名")
    private String type;

    @ApiModelProperty("此类型电影数量")
    private Integer typeCount;

    @ApiModelProperty("海报存储位置")
    private String posterUrl;

}
