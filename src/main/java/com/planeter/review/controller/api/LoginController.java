package com.planeter.review.controller.api;


import com.planeter.review.model.param.LoginParam;
import com.planeter.review.model.vo.UserVO;
import com.planeter.review.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.KeyStore;


/**
 * 登陆与注册
 */
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Resource
    private

    @PostMapping("/login")
    public UserVO login(@RequestBody @Validated LoginParam user) {
        return userService.login(user);
    }

    @PostMapping("/test")
    public String test() {
        log.info("---test---");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.toString());
        return "认证通过";
    }
}