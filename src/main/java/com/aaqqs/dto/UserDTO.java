package com.aaqqs.dto;

import lombok.Data;

/**
 * 用户信息缓存，展示的用户信息
 */
@Data
public class UserDTO {
    /**
     * 昵称
     */
    private String nickName;
    /**
     * id
     */
    private String id;
    /**
     * 头像
     */
    private String icon;
}
