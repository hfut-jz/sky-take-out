package com.sky.aspect;


import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import nonapi.io.github.classgraph.utils.Join;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Component
@Aspect
//进行切面编程
public class AutoFillAspect {
    //寻找其的所有的方法
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}

    //定义通知来增强方法,且其为前置通知，用来赋值
    @Before("autoFillPointCut()")

    public void autoFill(JoinPoint joinPoint){
        log.info("开始自动填充");
        //完成四个填充的基本步骤：
        //1.获取数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();
        //2.获取操作对象
        //保证实体类放在第一位即可
        Object[] args = joinPoint.getArgs();
        if(args==null||args.length==0){
            return ;
        }
        Object entity=args[0];
        //3.获取公共字段
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        //根据操作类型赋值不同操作
        if(operationType==OperationType.INSERT){

            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod("SetCreateUser", Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);

                setCreateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
                setUpdateTime.invoke(entity,now);
                setCreateUser.invoke(entity,currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (operationType==OperationType.UPDATE) {
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
    

}
