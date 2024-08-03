package com.aaqqs.common.convention.result;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 全局返回结果实体类
 * @param <T>
 */
@Data
@Accessors(chain = true)
public class Result<T> {
    /**
     * 状态码
     */
    private String code;
    /**
     * 状态信息
     */
    private String message;
    /**
     * 数据data
     */
    private T data;
    /**
     * 请求ID
     * 如果你的系统需要跟踪请求、调试问题、记录日志或进行性能监控，那么在Result类中添加requestID是有益的。
     */
    private String requestID;
}
