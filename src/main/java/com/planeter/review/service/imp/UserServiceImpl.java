package com.planeter.review.service.imp;


import com.planeter.review.common.annotation.Cache;
import com.planeter.review.common.exception.ApiException;
import com.planeter.review.model.entity.UserEntity;
import com.planeter.review.model.param.LoginParam;
import com.planeter.review.model.param.RegisterParam;
import com.planeter.review.model.vo.UserVO;
import com.planeter.review.repository.UserRepository;
import com.planeter.review.service.UserService;
import com.planeter.review.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    JavaMailSender javaMailSender;

    @Override
    public void verifyEmail(String email) {
        if (!email.matches("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$"))
            throw new ApiException("邮箱格式错误");
        // 生成4位数字验证码
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int randNumber = r.nextInt(10);
            sb.append(randNumber);
        }
        // 生成邮件
        String code = sb.toString();
        // 发送邮件
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,"utf-8");
            messageHelper.setFrom("planeter@126.com","Memory Booster");
            messageHelper.setTo(email);
            messageHelper.setSubject("Memory Booster注册邮箱验证");
            messageHelper.setText("验证码:" + code+", 五分钟后失效");
            javaMailSender.send(message);
            log.info("Send success!");
            // 将验证码存入Redis, 5min后失效
            redisTemplate.opsForValue().set("Reg-" + email, code, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long register(@Validated RegisterParam param) {
        if (userRepository.existsByUsername(param.getUsername()))
            throw new ApiException("邮箱已被使用");
        if (!param.getVerifyCode().equals(redisTemplate.opsForValue().get("Reg-" + param.getUsername())))
            throw new ApiException("验证码错误");
        UserEntity user = new UserEntity();
        user.setUsername(param.getUsername());
        user.setPassword(BCrypt.hashpw(param.getPassword(), BCrypt.gensalt()));
        return userRepository.save(user).getId();
    }

    @Override
    @CachePut(value = {"User"}, key = "#user.username")
    public UserEntity update(UserVO user) {
        UserEntity userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        if(user.getPassword()!=null)
            userEntity.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        if(user.getNickname()!=null)
            userEntity.setNickname(user.getNickname());
        if(user.getIcon()!=null)
            userEntity.setIcon(user.getIcon());
        return userRepository.save(userEntity);
    }
    @Override
    @Cache(value = "User", key = "#username")
    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
