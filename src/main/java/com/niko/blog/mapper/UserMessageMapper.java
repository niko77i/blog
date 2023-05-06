package com.niko.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niko.blog.entiy.pojo.UserMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niko.blog.entiy.vo.UserMessageVo;
import org.apache.ibatis.annotations.Param;

/**
* @author 阳
* @description 针对表【user_message】的数据库操作Mapper
* @createDate 2023-01-05 14:37:43
* @Entity com.niko.blog.entiy.pojo.UserMessage
*/
public interface UserMessageMapper extends BaseMapper<UserMessage> {

    /**
     * 分页查询对登录用户的回复
     * @param page
     * @param wrapper
     * @return
     */
    IPage<UserMessageVo> selectMessages(Page page,@Param(Constants.WRAPPER) LambdaQueryWrapper<UserMessage> wrapper);
}




