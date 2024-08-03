package com.aaqqs.controller;

import com.aaqqs.common.convention.result.Result;
import com.aaqqs.common.convention.result.Results;
import com.aaqqs.dto.request.UserLoginRequestDTO;
import com.aaqqs.dto.response.UserLoginResponseDTO;
import com.aaqqs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    /**
     * 发送手机短信验证码
     */
    @PostMapping("/user/send")
    public Result sendCode(@RequestParam String phone){
        userService.sendCode(phone);
        return Results.success();
    }
    /**
     * 用户登录
     */
    @PostMapping("user/login")
    public Result<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO requestParam){
        return Results.success(userService.login(requestParam));
    }
    /**
     * 获取用户信息
     */
}
