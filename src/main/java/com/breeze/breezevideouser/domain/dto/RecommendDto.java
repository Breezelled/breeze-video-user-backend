package com.breeze.breezevideouser.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author breeze
 * @date 2023/4/23 14:44
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "RecommendDto对象", description = "RecommendDto")
public class RecommendDto {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Integer user_id;

    @ApiModelProperty("喜欢序列")
    private String liked_sequence;

    @ApiModelProperty("感兴趣的类型")
    private String interest_type;

    @ApiModelProperty("影片id")
    private Integer info_id;

    @ApiModelProperty("影片类型")
    private String type;

    @ApiModelProperty("收藏序列")
    private String favorited_sequence;

    @ApiModelProperty("性别")
    private String gender;
}
