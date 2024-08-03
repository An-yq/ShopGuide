package com.aaqqs.common.convention.result;

import cn.hutool.core.util.StrUtil;
import com.aaqqs.common.convention.errorcode.BaseErrorCode;
import com.aaqqs.common.convention.exception.AbstractException;

import javax.swing.text.html.Option;
import java.util.Optional;

import static com.aaqqs.common.convention.errorcode.BaseErrorCode.SUCCESS;
import static com.aaqqs.common.convention.errorcode.BaseErrorCode.SYSTEM_ERROR_B0001;
import static java.util.stream.DoubleStream.builder;

/**
 * 构建响应体
 */
public class Results {
    /**
     * 构建返回成功响应体
     * @return
     */
    public static Result<Void> success(){
        return new Result<Void>()
                .setCode(SUCCESS.code())
                .setMessage(SUCCESS.message());
    }
    /**
     * 构建带数据的返回成功响应体
     */
    public static <T> Result<T> success(T data){
        return new Result<T>()
                .setCode(SUCCESS.code())
                .setMessage(SUCCESS.message())
                .setData(data);
    }

    /**
     * 根据AbstractException构建失败响应体
     * 用于异常处理器捕获异常
     */
    public static Result<Void> fail(AbstractException abstractException){
        String errorCode = Optional.ofNullable(abstractException.getErrorCode())
                .orElse(SYSTEM_ERROR_B0001.code());
        String errorMessage = Optional.ofNullable(abstractException.getErrorMessage())
                .orElse(SYSTEM_ERROR_B0001.message());
        return new Result<Void>()
                .setCode(errorCode)
                .setMessage(errorMessage);
    }

    /**
     * 根据errorCode errorMessage
     */
    public static Result<Void> fail(String errorCode,String errorMessage){
        return new Result<Void>()
                .setCode(errorCode)
                .setMessage(errorMessage);
    }
    /**
     * 无参数构建异常响应 -- 系统执行出错
     */
    public static Result<Void> fail(){
       return new Result<Void>()
               .setCode(SYSTEM_ERROR_B0001.code())
               .setMessage(SYSTEM_ERROR_B0001.message());
    }
}
