package com.breeze.breezevideouser.log.annotation;


import java.lang.annotation.*;

/**
 * @author breeze
 * 日志描述信息
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebLog {
    String description() default "";
}
