package com.aaqqs.common.convention.errorcode;

import lombok.Data;

/**
 * 异常码接口
 */
public interface IErrorCode {
    /**
     * 异常码
     */
    String code();
    /**
     * 返回异常信息
     */
    String message();
}
