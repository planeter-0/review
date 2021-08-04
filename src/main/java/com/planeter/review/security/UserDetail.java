package com.planeter.review.security;


import com.planeter.review.model.entity.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 自定义用户对象
 *
 * @author RudeCrab
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserDetail extends User {
    /**
     * 用户实体对象
     */
    private UserEntity userEntity;

    public UserDetail(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
        // 调用父类的构造方法，初始化用户名、密码、权限
        super(userEntity.getUsername(), userEntity.getPassword(), authorities);
        this.userEntity = userEntity;
    }
}
