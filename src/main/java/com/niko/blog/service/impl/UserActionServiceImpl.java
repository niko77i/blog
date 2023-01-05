package com.niko.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niko.blog.entiy.pojo.UserAction;
import com.niko.blog.service.UserActionService;
import com.niko.blog.mapper.UserActionMapper;
import org.springframework.stereotype.Service;

/**
* @author 阳
* @description 针对表【user_action】的数据库操作Service实现
* @createDate 2023-01-05 14:37:36
*/
@Service
public class UserActionServiceImpl extends ServiceImpl<UserActionMapper, UserAction>
    implements UserActionService{

}




