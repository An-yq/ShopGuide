package com.aaqqs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.aaqqs.common.constant.RedisConstant;
import com.aaqqs.common.constant.UserConstant;
import com.aaqqs.common.convention.exception.ClientException;
import com.aaqqs.common.convention.result.Result;
import com.aaqqs.dao.entity.UserDO;
import com.aaqqs.dao.mapper.UserMapper;
import com.aaqqs.dto.UserDTO;
import com.aaqqs.dto.request.UserLoginRequestDTO;
import com.aaqqs.dto.response.UserLoginResponseDTO;
import com.aaqqs.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.aaqqs.common.constant.RedisConstant.*;
import static com.aaqqs.common.constant.UserConstant.USER_NICKNAME_PREFIX;
import static com.aaqqs.common.convention.errorcode.BaseErrorCode.USER_ERROR_A0131;
import static com.aaqqs.common.convention.errorcode.BaseErrorCode.USER_ERROR_A0151;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    private final StringRedisTemplate stringRedisTemplate;
    @Override
    public void sendCode(String phone) {
        //1. 判断手机号是否合法
        if (!Validator.isMobile(phone)) {
            //2. 不合法，抛出异常 -- 手机号格式错误
            throw new ClientException(USER_ERROR_A0151);
        }
        //3. 合法，生成六位短信验证码
        String code = RandomUtil.randomNumbers(6);
        //4. 将短信验证码保存到redis中
        stringRedisTemplate.opsForValue().set(VER_CODE_PREFIX + phone,code,10, TimeUnit.MINUTES);
    }

    @Override
    public UserLoginResponseDTO login(UserLoginRequestDTO requestParam) {
        //1. 判断验证码是否有效
        String code = stringRedisTemplate.opsForValue().get(VER_CODE_PREFIX + requestParam.getPhone());
        if(StrUtil.isEmpty(code) || !requestParam.getCode().equals(code)){
            //抛出短信校验码输入错误异常
            throw new ClientException(USER_ERROR_A0131);
        }
        //2. 查询数据库是否存在该用户
        LambdaQueryWrapper<UserDO> wrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getPhone, requestParam.getPhone());
        UserDO userDO = baseMapper.selectOne(wrapper);
        //3. 不存在，创建一个用户保存到数据库
        if(userDO == null){
            userDO = UserDO.builder()
                    .phone(requestParam.getPhone())
                    .nickName(USER_NICKNAME_PREFIX + RandomUtil.randomString(10))
                    .createTime(new DateTime())
                    .build();
            baseMapper.insert(userDO);
        }
        //4. 生成用户token
        String token = UUID.randomUUID().toString(true);
        //5. 将用户信息中要展示的部分
        UserDTO userDTO = BeanUtil.toBean(userDO, UserDTO.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create().setIgnoreNullValue(true)
                        //这里lambda表达式 是为了实现FieldValueEditor的接口，参数为fieldName,fieldValue
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        //6. 将用户信息保存在Redis中
        stringRedisTemplate.opsForHash().putAll(USER_INFO_PREFIX + token,userMap);
        //7. 设置用户信息保存有效期
        stringRedisTemplate.expire(USER_INFO_PREFIX + token,USER_INFO_VALID_TIME,TimeUnit.MINUTES);
        return UserLoginResponseDTO.builder().token(token).build();
    }
}
