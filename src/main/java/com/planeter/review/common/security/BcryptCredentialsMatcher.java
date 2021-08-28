package com.planeter.review.common.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.mindrot.jbcrypt.BCrypt;

/**
 * UsernamePasswordToken凭证匹配器
 */
public class BcryptCredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        //要验证的明文密码
        String plaintext = new String(userToken.getPassword());
        //数据库中的加密后的密文
        String hashed = info.getCredentials().toString();
        return BCrypt.checkpw(plaintext, hashed);
    }
}
