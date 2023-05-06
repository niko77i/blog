package com.niko.blog.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niko.blog.entiy.pojo.Comment;
import com.niko.blog.entiy.vo.AskMess;
import com.niko.blog.entiy.vo.CommentVo;
import com.niko.blog.service.CommentService;
import com.niko.blog.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 阳
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2023-01-05 14:37:14
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Resource
    private CommentMapper commentMapper;

    /**
     * 获取评论的IPage对象
     * @param page
     * @param postId
     * @param userId
     * @param order
     * @return
     */
    @Override
    public IPage<CommentVo> pageVo(Page page, Long postId, Long userId, String order) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(postId != null, Comment::getPostId,postId).eq(userId != null,Comment::getUserId,userId)
                        .orderByDesc(Comment::getVoteUp);

        return commentMapper.selectComments(page,wrapper);
    }

    /**
     * 相关回答
     * @param userId
     * @return
     */
    @Override
    public List<AskMess> getAskMess(Long userId) {
        List<AskMess> askMessList = commentMapper.getAskMess(userId);
        return askMessList;
    }
}




