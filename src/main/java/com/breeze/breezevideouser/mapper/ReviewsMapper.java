package com.breeze.breezevideouser.mapper;

import com.breeze.breezevideouser.domain.Reviews;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * <p>
 * 电影评论 Mapper 接口
 * </p>
 *
 * @author breeze
 * @since 2022-12-20
 */
public interface ReviewsMapper extends BaseMapper<Reviews> {
    /**
     * 保存并返回当前保存的Id
     * @param reviews 待保存的评论
     * @return 当前评论Id
     */
    @Insert("insert into reviews (movie_id, author, date, title, content, ip_area, user_rating, sentiment_rating) " +
            "values (#{movieId}, #{author}, #{date}, #{title}, #{content}, #{ipArea}, #{userRating}, " +
            "#{sentimentRating}) returning id")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer saveAndGetId(Reviews reviews);
}
