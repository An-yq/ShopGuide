package com.aaqqs.common.constant;

/**
 * Redis 缓存常量类
 */
public class RedisConstant {
    /**
     * 保存用户验证码key前缀
     */
    public static final String VER_CODE_PREFIX = "login:code:phone:";
    /**
     * 用户信息保存前缀
     */
    public static final String USER_INFO_PREFIX = "user:";
    /**
     * 用户信息保存有效期
     */
    public static final Long USER_INFO_VALID_TIME = 30L;
}
