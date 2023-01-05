package com.niko.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niko.blog.entiy.pojo.User;
import com.niko.blog.service.UserService;
import com.niko.blog.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 阳
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-01-05 14:37:32
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




