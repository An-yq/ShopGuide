package com.aaqqs.service;

import com.aaqqs.common.convention.result.Result;
import com.aaqqs.dao.entity.UserDO;
import com.aaqqs.dto.request.UserLoginRequestDTO;
import com.aaqqs.dto.response.UserLoginResponseDTO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<UserDO> {
    /**
     * 发送验证码功能
     */
    void sendCode(String phone);

    /**
     * 用户登录功能
     * @param requestParam 用户登录请求实体
     * @return token
     */
    UserLoginResponseDTO login(UserLoginRequestDTO requestParam);
}
