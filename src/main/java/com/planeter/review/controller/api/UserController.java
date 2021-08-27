package com.planeter.review.controller.api;


import com.planeter.review.model.entity.ScoreBoard;
import com.planeter.review.model.entity.UserEntity;
import com.planeter.review.model.param.LoginParam;
import com.planeter.review.model.param.RegisterParam;
import com.planeter.review.model.vo.ResultVO;
import com.planeter.review.service.ScoreBoardService;
import com.planeter.review.service.UserService;
import com.planeter.review.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;


/**
 * 登陆与注册
 */
@Slf4j
@RestController
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private ScoreBoardService scoreBoardService;
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/register/verify")
    public ResultVO<String> sendEmail(@RequestParam String email) {
        userService.verifyEmail(email);
        return new ResultVO<>("验证码发送成功");
    }

    @PostMapping("/register")
    public ResultVO<String> register(@RequestBody @Validated RegisterParam param) {
        // 注册并创建计分板
        scoreBoardService.createScoreBoard(userService.register(param));
        return new ResultVO<>("注册成功");
    }

    @PostMapping("/login")
    public UserEntity login(@RequestBody @Validated LoginParam param, HttpServletResponse response) {
        String username = param.getUsername();
        String password = param.getPassword();
        Subject subject = SecurityUtils.getSubject();
        UserEntity user = null;
        // 登录
        subject.login(new UsernamePasswordToken(username, password));
        user = (UserEntity) subject.getPrincipal();
        // 签发Jwt,存入Redis
        String jwt = JwtUtils.sign(username, 9000);
        redisTemplate.opsForValue().set("Jwt-" + param.getUsername(), jwt, 9000, TimeUnit.SECONDS);
        // 设置token
        response.setHeader("Set-Token", jwt);
        return user;
    }

}