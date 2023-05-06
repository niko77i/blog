package com.niko.blog.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.google.code.kaptcha.Producer;
import com.niko.blog.common.lang.Result;
import com.niko.blog.controller.BaseController;
import com.niko.blog.entiy.pojo.User;
import com.niko.blog.util.ValidationUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class AuthController extends BaseController {

    private static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

    @Autowired
    private Producer producer;

    @GetMapping("/kaptcha.jpg")
    public void kaptcha(HttpServletResponse resp) throws IOException {
        //验证码
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);
        req.getSession().setAttribute(KAPTCHA_SESSION_KEY, text);

        resp.setHeader("Cache-Control", "no-store,no-catch");
        resp.setContentType("image/jpeg");
        ServletOutputStream outputStream = resp.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
    }

    @GetMapping("/login")
    public String login() {

        return "/auth/login";
    }

    @ResponseBody
    @PostMapping("/login")
    public Result doLogin(String email, String password, String vercode) {
        //获得生成的图片验证码
        String kaptcha = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);

        if (!vercode.equals(kaptcha)) {
            return Result.fail("验证码错误");
        }

        if (StrUtil.isEmpty(email) || StrUtil.isEmpty(password)) {
            return Result.fail("邮箱或密码不能为空");
        }

        UsernamePasswordToken token = new UsernamePasswordToken(email, SecureUtil.md5(password));

        try {
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException) {
                return Result.fail("用户不存在");
            } else if (e instanceof LockedAccountException) {
                return Result.fail("用户被禁用");
            } else if (e instanceof IncorrectCredentialsException) {
                return Result.fail("密码错误");
            } else {
                return Result.fail("用户认证失败");
            }
        }

        return Result.success().action("/");
    }


    @GetMapping("/register")
    public String register() {

        return "/auth/reg";
    }

    @ResponseBody
    @PostMapping("/register")
    public Result doRegister(User user, String repass, String vercode) {

        //判断用户名，密码，邮箱是否符合要求
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(user);
        if (validResult.hasErrors()) {
            return Result.fail(validResult.getErrors());
        }

        if (!user.getPassword().equals(repass)) {
            return Result.fail("两次输入的密码不正确");
        }


        //获得生成的图片验证码
        String kaptcha = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        System.out.println(kaptcha);

        //验证图片验证码是否正确
        if (vercode == null || !vercode.equalsIgnoreCase(kaptcha)) {
            return Result.fail("验证码不正确");
        }

        //判断是否注册过
        if (!userService.registerDown(user)) {
            Result result = userService.register(user);
            return result;
        }


        return Result.fail("你已注册过或邮箱被占用");
    }

    @RequestMapping ("/user/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }
}
