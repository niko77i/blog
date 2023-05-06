package com.niko.blog.shiro;

import com.niko.blog.entiy.vo.AccountVo;
import com.niko.blog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AccountVo accountVo = (AccountVo) principalCollection.getPrimaryPrincipal();

        //给id为13的用户赋予管理员权限
        if (accountVo.getId() == 13){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("admin");
            return info;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

        AccountVo accountVo = userService.login(usernamePasswordToken.getUsername(),String.valueOf(usernamePasswordToken.getPassword()));

        //登录之后将用户信息放到session中
        SecurityUtils.getSubject().getSession().setAttribute("accountVo",accountVo);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(accountVo,authenticationToken.getCredentials(),getName());

        return info;
    }
}
