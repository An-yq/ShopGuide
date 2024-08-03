package com.aaqqs.tookit;

import com.aaqqs.dto.UserDTO;

/**
 * 用户上下文，保存在ThreadLocal中
 */
public class UserContext {
    public static final ThreadLocal<UserDTO> threadLocal = new ThreadLocal<>();
    /**
     * 保存用户
     */
    public static void saveUser(UserDTO userDTO){
        threadLocal.set(userDTO);
    }
    /**
     * 删除用户
     */
    public static void removeUser(){
        threadLocal.remove();
    }
    /**
     * 获取用户
     */
    public static UserDTO getUser(){
        return threadLocal.get();
    }
}
