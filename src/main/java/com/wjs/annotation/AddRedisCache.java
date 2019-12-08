package com.wjs.annotation;

import java.lang.annotation.*;

/**
 * Created by HIAPAD on 2019/11/28.
 * 自定义添加缓存注解类
 */
@Documented  //说明该注解将被包含在javadoc中
@Retention(RetentionPolicy.RUNTIME)  // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target(ElementType.METHOD)  //这个注解就是表明该注解类能够作用的范围，也就是能够注解在哪，比如 类、方法、参数等。
public @interface AddRedisCache {

}