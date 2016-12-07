package com.bytebeats.pandora.annotation;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-08 00:05
 */
public @interface Extension {
    String module() default ""; //模块名
    boolean internal() default false;
    String path() default "";   //扩展jar路径
    String name() default "";    //扩展实现全类名
}
