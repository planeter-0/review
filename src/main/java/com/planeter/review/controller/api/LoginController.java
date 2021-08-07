package com.planeter.review.controller.api;


import com.planeter.review.model.param.LoginParam;
import com.planeter.review.model.param.RegisterParam;
import com.planeter.review.model.vo.ResultVO;
import com.planeter.review.model.vo.UserVO;
import com.planeter.review.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * 登陆与注册
 */
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @PostMapping("/login")
    public UserVO login(@RequestBody @Validated LoginParam user) {
        return userService.login(user);
    }
    @Value("${spring.mail.properties.from}")
    private String sender;

    @GetMapping("/register/email")
    public String sendEmail(@RequestParam String email) {
        if(!email.matches("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$"))
            return "邮箱格式不对";
        // 生成6位数字验证码
        Random r = new Random();
        StringBuffer sb =new StringBuffer();
        for(int i = 0;i < 6;i ++){
            int randNumber = r.nextInt(10);
            sb.append(randNumber);
        }
        // 生成邮件
        String code = sb.toString();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email);
        message.setSubject("Memory Booster");
        message.setText("验证码:"+code);
        // 发送邮件
        try {
            javaMailSender.send(message);
            log.info("Send success!");
            // 将验证码存入Redis
            redisTemplate.opsForValue().set("Reg-"+email,code, 5, TimeUnit.MINUTES);
            return "邮件发送成功";
        }catch (Exception e){
            e.printStackTrace();
            return "邮件发送失败";
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody @Validated RegisterParam param){
        userService.register(param);
        return "注册成功";
    }
}