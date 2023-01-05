package com.niko.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niko.blog.entiy.pojo.UserMessage;
import com.niko.blog.service.UserMessageService;
import com.niko.blog.mapper.UserMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author 阳
* @description 针对表【user_message】的数据库操作Service实现
* @createDate 2023-01-05 14:37:43
*/
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage>
    implements UserMessageService{

}




