package com.niko.blog.shiro;

import cn.hutool.json.JSONUtil;
import com.niko.blog.common.lang.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * shiro过滤器，判断是否是ajax请求
 */
public class AuthFilter extends UserFilter {
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        // ajax请求 弹窗显示未登录
        String header = httpServletRequest.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header)) {
            //判断是否登录
            boolean authenticated = SecurityUtils.getSubject().isAuthenticated();
            if (!authenticated){
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(JSONUtil.toJsonStr(Result.fail("请先登录!")));
            }
        }else {
            // web请求 重定向到登录页面
            super.redirectToLogin(request, response);
        }


    }
}
