package com.planeter.review.service;

import com.planeter.review.model.entity.UserEntity;
import com.planeter.review.model.param.LoginParam;
import com.planeter.review.model.param.RegisterParam;
import com.planeter.review.model.vo.UserVO;

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

    /**
     * 更新用户
     * @param user
     * @return
     */
    UserEntity update(UserVO user);

    /**
     * 根据用户名获取用户实体
     * @param username
     * @return
     */
    UserEntity getByUsername(String username);
}
