package com.niko.blog.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niko.blog.common.lang.Result;
import com.niko.blog.entiy.pojo.User;
import com.niko.blog.entiy.vo.AccountVo;
import com.niko.blog.service.UserService;
import com.niko.blog.mapper.UserMapper;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
* @author 阳
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-01-05 14:37:32
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public Result register(User user) {

        User temp = new User();
        temp.setUsername(user.getUsername());
        temp.setEmail(user.getEmail());
        temp.setPassword(SecureUtil.md5(user.getPassword()));
        temp.setCreated(new Date());
        temp.setPoint(0);
        temp.setVipLevel(0);
        temp.setCommentCount(0);
        temp.setPostCount(0);
        temp.setGender("0");
        temp.setAvatar("/res/images/avatar/default.png");

        boolean save = this.save(temp);

        if (save){
            return Result.success().action("/login");
        }

        return Result.fail("注册失败，请重试");
    }

    /**
     * 判断用户是否注册
     * @param user
     * @return
     */
    @Override
    public boolean registerDown(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,user.getEmail());
        User one = this.getOne(queryWrapper);
        if (one != null){
            return true;
        }
        return false;
    }


    /**
     * 登录
     * @param email
     * @param password
     * @return
     */
    @Override
    public AccountVo login(String email, String password) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail,email));

        if (ObjectUtils.isEmpty(user)){
            throw  new UnknownAccountException();
        } else if (!user.getPassword().equals(password)){
            throw new IncorrectCredentialsException();
        }
        //设置最后登录时间
        user.setLasted(new Date());
        //更新数据库
        this.updateById(user);

        //
        AccountVo accountVo = new AccountVo();
        BeanUtils.copyProperties(user,accountVo);

        return accountVo;
    }
}




