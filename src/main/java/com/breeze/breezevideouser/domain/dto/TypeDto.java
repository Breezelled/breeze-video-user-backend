package com.breeze.breezevideouser.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 影片类型DTO
 * </p>
 *
 * @author breeze
 * @since 2023-03-31
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "TypeDto对象", description = "保存用户Interest Type Dto")
public class TypeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户兴趣类型名")
    private List<String> types;

    @ApiModelProperty("用户邮箱")
    private String email;
}
