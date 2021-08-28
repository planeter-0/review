package com.planeter.review.common.security;

import com.planeter.review.model.entity.UserEntity;
import com.planeter.review.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class DbShiroRealm extends AuthorizingRealm {

    @Resource
    UserService userService;

    //设置凭证匹配器Bcrypt
    public DbShiroRealm() {
        this.setCredentialsMatcher(new BcryptCredentialsMatcher());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();//UsernameAndPasswordToken
        // 从数据库中查找user信息
        UserEntity user = userService.getByUsername(username);
        if (null == user)
            throw new AuthenticationException("用户不存在");
        //认证成功,返回认证info
        return new SimpleAuthenticationInfo(
                user, //principal
                user.getPassword(), //hashedCredential
                ByteSource.Util.bytes(username),
                getName() // realmName
        );
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }
}
