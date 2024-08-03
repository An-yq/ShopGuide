package com.aaqqs.common.convention.exception;

import com.aaqqs.common.convention.errorcode.BaseErrorCode;
import com.aaqqs.common.convention.errorcode.IErrorCode;
import com.mysql.cj.xdevapi.Client;

import static com.aaqqs.common.convention.errorcode.BaseErrorCode.USER_ERROR_0001;

/**
 * 客户端请求异常
 */
public class ClientException extends AbstractException{
    //父类的完整构造器
    public ClientException(Throwable throwable, IErrorCode errorCode, String message) {
        super(throwable, errorCode, message);
    }
    //自定义构造器
    public ClientException(IErrorCode errorCode){
        this(null,errorCode,null);
    }
    //返回BaseErrorCode
    public ClientException(String message){
        this(null, USER_ERROR_0001,message);
    }
    public ClientException(String message,IErrorCode errorCode){
        this(null,errorCode,message);
    }

}
