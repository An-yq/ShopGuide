package com.aaqqs.common.web;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.aaqqs.common.convention.exception.AbstractException;
import com.aaqqs.common.convention.result.Result;
import com.aaqqs.common.convention.result.Results;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

import static com.aaqqs.common.convention.errorcode.BaseErrorCode.USER_ERROR_0001;

@Slf4j
@Component
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result validExceptionHandler(HttpServletRequest request,MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        FieldError firstFieldError = CollectionUtil.getFirst(bindingResult.getFieldErrors());
        String exceptionStr = Optional.ofNullable(firstFieldError)
                .map(FieldError::getDefaultMessage)
                .orElse(StrUtil.EMPTY);
        //打印错误日志：请求类型+请求路径+异常信息
        log.error("[{}] {} [ex] {}", request.getMethod(), getUrl(request), exceptionStr);
        //使用异常码 + 异常信息构建异常响应
        return Results.fail(USER_ERROR_0001.code(), exceptionStr);
    }

    /**
     * 处理应用内抛出的异常
     */
    @ExceptionHandler(AbstractException.class)
    public Result applicationException(HttpServletRequest request, AbstractException ex){
        if (ex.getCause() != null) {
            log.error("[{}] {} [ex] {}", request.getMethod(), request.getRequestURL().toString(), ex.toString(), ex.getCause());
            return Results.fail(ex);
        }
        log.error("[{}] {} [ex] {}", request.getMethod(), request.getRequestURL().toString(), ex.toString());
        return Results.fail(ex);
    }
    /**
     * 处理其他未捕获的异常，当做系统执行错误处理
     */
    @ExceptionHandler(value = Throwable.class)
    public Result defaultErrorHandler(HttpServletRequest request, Throwable throwable) {
        log.error("[{}] {} ", request.getMethod(), getUrl(request), throwable);
        return Results.fail();
    }
    private String getUrl(HttpServletRequest request) {
        if(StrUtil.isEmpty(request.getQueryString())){
            return request.getRequestURL().toString();
        }
        return request.getRequestURL().toString() + "?" + request.getQueryString();
    }
}
