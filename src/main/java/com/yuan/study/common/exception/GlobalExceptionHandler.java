package com.yuan.study.common.exception;


import com.baomidou.mybatisplus.extension.api.R;
import com.yuan.study.common.Result;
import com.yuan.study.common.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * 通过使用@ControllerAdvice来进行统一异常处理
 * ,@ExceptionHandler(value = RuntimeException.class)来指定捕获的Exception各个类型异常 ，
 * 这个异常的处理，是全局的，所有类似的异常，都会跑到这个地方处理。
 * <p>
 * 定义全局异常处理，@ControllerAdvice表示定义全局控制器异常处理
 * ，@ExceptionHandler表示针对性异常处理，可对每种异常针对性处理。
 * <p>
 * 全局异常处理
 * IllegalArgumentException：处理Assert的异常
 * MethodArgumentNotValidException：处理实体校验的异常
 * RuntimeException：捕捉其他异常
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    public Result handleSqlException(SQLException e) {
        log.error("sql语句错误异常:{}", e.getMessage());
        return Result.fail(ResultCode.SQL_Exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        String errorMsg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error("参数校验错误异常:{}", errorMsg);
        return Result.fail(ResultCode.PARAM_VALID_Exception, errorMsg);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        log.error("父类异常:{}", e.getMessage());
        return Result.fail(ResultCode.UNKNOWN_EXCEPTION);
    }

}
