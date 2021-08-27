package com.planeter.review.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 登录参数
 */
@Data
public class LoginParam {
    @Email
    private String username;
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 12, message = "密码长度为6-12位")
    private String password;
}
