package com.aaqqs.common.web;

import cn.hutool.core.bean.BeanUtil;
import com.aaqqs.dto.UserDTO;
import com.aaqqs.tookit.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.aaqqs.common.constant.RedisConstant.USER_INFO_PREFIX;
import static com.aaqqs.common.constant.RedisConstant.USER_INFO_VALID_TIME;

/**
 * token有效期刷新时间
 * 只要用户在浏览，无论访问哪个接口，都要刷新token有效期
 */
@RequiredArgsConstructor
public class TokenRefreshInterceptor implements HandlerInterceptor {
    private final StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 请求参数中找到authorization的参数，里面存放的是token
        String token = request.getHeader("authorization");
        if(token == null){
            //直接放行
            return true;
        }
        //2. 查找token是否存在
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(USER_INFO_PREFIX + token);
        if(userMap.isEmpty()){
            return true;
        }
        //3. 将查到的userMap转化UserDTO保存到UserContext中
        UserDTO userDTO = new UserDTO();
        BeanUtil.fillBeanWithMap(userMap,userDTO,false);
        UserContext.saveUser(userDTO);
        //4. 将Redis中的有效期更新
        stringRedisTemplate.expire(USER_INFO_PREFIX + token,USER_INFO_VALID_TIME, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
    }
}
