package com.aaqqs.dto.request;

import lombok.Data;

/**
 * 用户登录请求实体
 */
@Data
public class UserLoginRequestDTO {
    /**
     * 手机号
     */
    private String phone;
    /**
     * 短信验证码
     */
    private String code;
}
