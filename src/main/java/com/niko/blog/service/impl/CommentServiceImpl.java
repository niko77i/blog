package com.niko.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niko.blog.entiy.pojo.Comment;
import com.niko.blog.service.CommentService;
import com.niko.blog.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author 阳
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2023-01-05 14:37:14
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




