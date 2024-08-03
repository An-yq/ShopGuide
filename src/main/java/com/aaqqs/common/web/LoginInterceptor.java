package com.aaqqs.common.web;

import com.aaqqs.common.convention.exception.ClientException;
import com.aaqqs.tookit.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.aaqqs.common.convention.errorcode.BaseErrorCode.USER_ERROR_A0200;

/**
 * 登录拦截器，对需要登录验证的api进行验证
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断用户token是否存在
        if(UserContext.getUser() == null){
            //不存在，拦截
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
