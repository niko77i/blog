package com.niko.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niko.blog.entiy.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niko.blog.entiy.vo.AskMess;
import com.niko.blog.entiy.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 阳
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2023-01-05 14:37:14
* @Entity com.niko.blog.entiy.pojo.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {

    IPage<CommentVo> selectComments(Page page, @Param(Constants.WRAPPER) LambdaQueryWrapper<Comment> wrapper);

    List<AskMess> getAskMess(Long userId);
}




