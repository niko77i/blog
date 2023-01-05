package com.niko.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niko.blog.entiy.pojo.Post;
import com.niko.blog.service.PostService;
import com.niko.blog.mapper.PostMapper;
import org.springframework.stereotype.Service;

/**
* @author 阳
* @description 针对表【post】的数据库操作Service实现
* @createDate 2023-01-05 14:37:28
*/
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService{

}




