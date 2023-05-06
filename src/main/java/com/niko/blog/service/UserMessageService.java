package com.niko.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niko.blog.entiy.pojo.UserMessage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 阳
* @description 针对表【user_message】的数据库操作Service
* @createDate 2023-01-05 14:37:43
*/
public interface UserMessageService extends IService<UserMessage> {

    /**
     * 分页查询对登录用户的回复
     * @param page
     * @param wrapper
     * @return
     */
    IPage pageing(Page page, LambdaQueryWrapper<UserMessage> wrapper);

    /**
     * 删除消息
     * @param messId
     * @param allRemove
     * @return
     */
    boolean del(Long messId, Boolean allRemove);
}
