package com.cny.mybatissource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : chennengyuan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NeedInterceptor {

    /**
     * 原表名称
     *
     * @return
     */
    String originalTableName();

    /**
     * 新表名称
     *
     * @return
     */
    String newTableName();

}
