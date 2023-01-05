package com.niko.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niko.blog.entiy.pojo.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niko.blog.entiy.vo.PostVo;
import org.apache.ibatis.annotations.Param;

/**
* @author 阳
* @description 针对表【post】的数据库操作Mapper
* @createDate 2023-01-05 14:37:28
* @Entity com.niko.blog.entiy.pojo.Post
*/
public interface PostMapper extends BaseMapper<Post> {

    IPage<PostVo> selectPosts(Page page, @Param(Constants.WRAPPER)LambdaQueryWrapper<Post> wrapper);

    PostVo selectOnePostVo(@Param(Constants.WRAPPER) QueryWrapper<Post> wrapper);
}




