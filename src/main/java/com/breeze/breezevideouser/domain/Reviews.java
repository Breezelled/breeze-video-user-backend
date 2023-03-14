package com.breeze.breezevideouser.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 电影评论
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "Reviews对象", description = "电影评论")
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("电影id")
    private Integer movieId;

    @ApiModelProperty("评论作者")
    private String author;

    @ApiModelProperty("评论日期")
    private String date;

    @ApiModelProperty("评论标题")
    private String title;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论时ip地区")
    private String ipArea;


}
