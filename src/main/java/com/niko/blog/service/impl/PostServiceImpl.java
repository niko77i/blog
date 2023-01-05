package com.niko.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niko.blog.entiy.pojo.Post;
import com.niko.blog.entiy.vo.PostVo;
import com.niko.blog.service.PostService;
import com.niko.blog.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 阳
 * @description 针对表【post】的数据库操作Service实现
 * @createDate 2023-01-05 14:37:28
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
        implements PostService {

    @Resource
    private PostMapper postMapper;

    @Override
    public IPage<PostVo> pageVo(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String created) {

        if (level == null) level = -1;
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(categoryId != null, Post::getCategoryId, categoryId)
                .eq(userId != null, Post::getUserId, userId)
                .eq(level == 0, Post::getLevel, 0)
                .gt(level > 0, Post::getLevel, 0)
                .orderByDesc(created != null, Post::getCreated);
        return postMapper.selectPosts(page, wrapper);
    }

    @Override
    public PostVo selectOnePost(QueryWrapper<Post> wrapper) {

        return postMapper.selectOnePostVo(wrapper);
    }
}




