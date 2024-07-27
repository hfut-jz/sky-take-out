package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.exception.DuplicateException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
    /**
     * 捕获数据库主键id重复异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        String message= ex.getMessage();
        if(message.contains("Duplicate entry")){
            //提示前端是字符串异常
            //正常后端开发不会用黑马这种这么原始的方法的，可以重新定义一个异常类来优化流程。
            DuplicateException duplicateException;
            return Result.error(MessageConstant.ALREADY_EXISTS);
        }
        else{
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
