package com.niko.blog.service;

import com.niko.blog.common.lang.Result;
import com.niko.blog.entiy.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.niko.blog.entiy.vo.AccountVo;

/**
* @author 阳
* @description 针对表【user】的数据库操作Service
* @createDate 2023-01-05 14:37:32
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param user
     * @return
     */
    Result register(User user);

    /**
     * 判断用户是否注册
     * @param user
     * @return
     */
    boolean registerDown(User user);

    /**
     * 登录
     * @param email
     * @param password
     * @return
     */
    AccountVo login(String email, String password);
}
