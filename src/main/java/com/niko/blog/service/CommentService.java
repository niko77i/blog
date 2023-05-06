package com.niko.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niko.blog.entiy.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.niko.blog.entiy.vo.AskMess;
import com.niko.blog.entiy.vo.CommentVo;

import java.util.List;

/**
* @author 阳
* @description 针对表【comment】的数据库操作Service
* @createDate 2023-01-05 14:37:14
*/
public interface CommentService extends IService<Comment> {

    /**
     * 获取评论的IPage对象
     * @param page
     * @param postId
     * @param userId
     * @param order
     * @return
     */
    IPage<CommentVo> pageVo(Page page, Long postId, Long userId, String order);


    /**
     * 相关回答
     * @param userId
     * @return
     */
    List<AskMess> getAskMess(Long userId);
}
