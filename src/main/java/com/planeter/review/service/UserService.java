package com.planeter.review.service;

import com.planeter.review.model.param.LoginParam;
import com.planeter.review.model.vo.UserVO;

public interface UserService {
    UserVO login(LoginParam user);
}
