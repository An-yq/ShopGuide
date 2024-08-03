package com.aaqqs.common.convention.exception;

import com.aaqqs.common.convention.errorcode.IErrorCode;

import static com.aaqqs.common.convention.errorcode.BaseErrorCode.SERVICE_ERROR_C0001;
import static com.aaqqs.common.convention.errorcode.BaseErrorCode.USER_ERROR_0001;

/**
 * 调用第三方服务出错
 */
public class ServiceException extends AbstractException{
    public ServiceException(Throwable throwable, IErrorCode errorCode, String message) {
        super(throwable, errorCode, message);
    }
    //自定义构造器
    public ServiceException(IErrorCode errorCode){
        this(null,errorCode,null);
    }
    //返回BaseErrorCode
    public ServiceException(String message){
        this(null, SERVICE_ERROR_C0001,message);
    }
    public ServiceException(String message,IErrorCode errorCode){
        this(null,errorCode,message);
    }
}
