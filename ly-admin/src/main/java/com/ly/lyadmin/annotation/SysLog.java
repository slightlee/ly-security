package com.ly.lyadmin.annotation;

import java.lang.annotation.*;


/**
 * @Description: TODO
 * 
 * @Author: SLIGHTLEE
 * @Date: 2019/10/15 11:03 下午
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";

}