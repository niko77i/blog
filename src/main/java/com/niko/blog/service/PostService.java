package com.niko.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niko.blog.entiy.pojo.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.niko.blog.entiy.vo.PostVo;

/**
* @author 阳
* @description 针对表【post】的数据库操作Service
* @createDate 2023-01-05 14:37:28
*/
public interface PostService extends IService<Post> {


    /**
     * 分页查询
     * @param page
     * @param categoryId
     * @param userId
     * @param level
     * @param recommend
     * @param created
     * @return
     */
    IPage<PostVo> pageVo(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String created);

    /**
     * 查询文章信息
     * @param wrapper
     * @return
     */
    PostVo selectOnePost(QueryWrapper<Post> wrapper);

    /**
     * 本章热议文章初始化
     */
    void initWeekRank();

    /**
     * 对新添加的评论进行缓存处理
     * @param postId
     * @param isIncr
     */
    void incrCommentCountAndUnionForWeekRank(long postId,boolean isIncr);

    /**
     * 对文章的观看次数做缓存处理，每次刷新页面或点击文章就会刷新一次
     * @param postVo
     */
    void putViewCount(PostVo postVo);
}
