package com.fechin.web.shiro;

import common.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;
        //注意:usernamePasswordToken调用Principal()方法实际上返回的是username;
        String email = token1.getUsername();
        String password = new String(token1.getPassword());

        String mdPassword = Encrypt.md5(password, email);
        String credentials = (String) info.getCredentials();
        if (mdPassword.equals(credentials)){
            return true;
        }
        return false;
    }
}
