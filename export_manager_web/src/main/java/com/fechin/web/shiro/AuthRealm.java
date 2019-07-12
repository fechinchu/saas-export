package com.fechin.web.shiro;

import com.fechin.domain.module.Module;
import com.fechin.domain.system.user.User;
import com.fechin.service.module.ModuleService;
import com.fechin.service.system.user.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String email = usernamePasswordToken.getUsername();
        //String password = new String(usernamePasswordToken.getPassword());
        User user = userService.findByEmail(email);
        if (user == null) {
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        return info;


    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //通过principals获取到User
        User user = (User) principals.getPrimaryPrincipal();

        List<Module> moduleByUid = moduleService.findModuleByUid(user);
        HashSet<String> set = new HashSet<>();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for (Module module : moduleByUid) {
            set.add(module.getName());
        }
        authorizationInfo.setStringPermissions(set);
        return authorizationInfo;
    }
}
