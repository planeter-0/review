package com.planeter.review.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/8/4 15:49
 * @status dev
 */
@Data
public class RegisterParam {
    @Email
    String username;
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 12, message = "密码长度为6-12位")
    String password;
    @NotBlank(message = "验证码不能为空")
    String verifyCode;
}
