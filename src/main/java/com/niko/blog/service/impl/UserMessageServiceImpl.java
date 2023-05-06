package com.niko.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niko.blog.entiy.pojo.UserMessage;
import com.niko.blog.service.UserMessageService;
import com.niko.blog.mapper.UserMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 阳
* @description 针对表【user_message】的数据库操作Service实现
* @createDate 2023-01-05 14:37:43
*/
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage>
    implements UserMessageService{

    @Autowired
    private UserMessageMapper userMessageMapper;

    /**
     * 分页查询对登录用户的回复
     * @param page
     * @param wrapper
     * @return
     */
    @Override
    public IPage pageing(Page page, LambdaQueryWrapper<UserMessage> wrapper) {
        return userMessageMapper.selectMessages(page,wrapper);
    }

    /**
     * 删除消息
     * @param messId
     * @param allRemove
     * @return
     */
    @Override
    public boolean del(Long messId, Boolean allRemove) {
        return false;
    }
}




