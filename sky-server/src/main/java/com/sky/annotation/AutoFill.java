package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//注解类，生成多个注解，来使得类语言的冗余的减少
//强调注解只能加在方法上
@Target(ElementType.METHOD)
//表明注解在其运行时依然是有效的。
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill{
    //自动填充字段的需要：UPDATE,INSERT
    OperationType value();


}

