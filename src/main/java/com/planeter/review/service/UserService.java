package com.planeter.review.service;

import com.planeter.review.model.entity.UserEntity;
import com.planeter.review.model.param.LoginParam;
import com.planeter.review.model.param.RegisterParam;

public interface UserService {
    /**
     * 注册邮箱验证
     * @param email
     */
    void verifyEmail(String email);

    /**
     * 注册
     * @param param
     * @return 用户id
     */
    Long register(RegisterParam param);
}
