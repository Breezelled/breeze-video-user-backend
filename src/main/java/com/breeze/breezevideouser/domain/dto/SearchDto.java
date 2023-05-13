package com.breeze.breezevideouser.domain.dto;

import java.io.Serializable;

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
@ApiModel(value = "搜索内容Dto对象", description = "搜索内容Dto对象")
public class SearchDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户uuid")
    private String content;

}
