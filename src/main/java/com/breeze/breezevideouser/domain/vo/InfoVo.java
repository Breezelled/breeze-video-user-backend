package com.breeze.breezevideouser.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 电影信息
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel(value = "InfoVo对象", description = "电影信息Vo")
public class InfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("电影名")
    private String name;

    @ApiModelProperty("电影时长")
    private String runtime;

    @ApiModelProperty("电影类型")
    private String type;

    @ApiModelProperty("上映日期")
    private String releaseDate;

    @ApiModelProperty("介绍")
    private String intro;

    @ApiModelProperty("导演")
    private String director;

    @ApiModelProperty("编剧")
    private String writer;

    @ApiModelProperty("明星")
    private String star;

    @ApiModelProperty("电影预算")
    private String budget;

    @ApiModelProperty("电影票房")
    private String revenue;

    @ApiModelProperty("电影语言")
    private String language;

    @ApiModelProperty("电影公司")
    private String company;

    @ApiModelProperty("国家地区")
    private String country;

    @ApiModelProperty("电影评分")
    private String rating;

    @ApiModelProperty("电影评分数")
    private String ratingNum;

    @ApiModelProperty("电影宣传语")
    private String tag;

    @ApiModelProperty("海报存储位置")
    private String posterUrl;

    @ApiModelProperty("预告片存储位置")
    private String trailerUrl;

}
