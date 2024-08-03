package com.aaqqs.common.convention.exception;

import com.aaqqs.common.convention.errorcode.IErrorCode;

import static com.aaqqs.common.convention.errorcode.BaseErrorCode.SYSTEM_ERROR_B0001;

/**
 * 系统执行出错
 */
public class SystemException extends AbstractException{
    public SystemException(Throwable throwable, IErrorCode errorCode, String message) {
        super(throwable, errorCode, message);
    }
    //自定义构造器
    public SystemException(IErrorCode errorCode){
        this(null,errorCode,null);
    }
    //返回BaseErrorCode
    public SystemException(String message){
        this(null, SYSTEM_ERROR_B0001,message);
    }
    public SystemException(String message,IErrorCode errorCode){
        this(null,errorCode,message);
    }
}
