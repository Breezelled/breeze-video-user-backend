package com.breeze.breezevideouser.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author breeze
 * @date 2023/4/17 00:20
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "SentimentDto对象", description = "SentimentDto")
public class SentimentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分析内容")
    private String content;

}
