package com.aaqqs.common.convention.exception;

import cn.hutool.core.util.StrUtil;
import com.aaqqs.common.convention.errorcode.IErrorCode;
import lombok.Data;
import lombok.Getter;

import java.util.Optional;

/**
 * 异常处理抽象类
 */
@Getter
public abstract class AbstractException extends RuntimeException{
    //异常码
    private final String errorCode;
    //异常信息
    private final String errorMessage;
    public AbstractException(Throwable throwable, IErrorCode errorCode,String message){
        super(message,throwable);
        this.errorCode = errorCode.code();
        //如果异常信息为空，就用自定义的errorCode的message
        this.errorMessage = Optional.ofNullable(StrUtil.isEmpty(message)?message:null).orElse(errorCode.message());
    }
}
