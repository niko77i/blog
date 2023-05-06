package com.niko.blog.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niko.blog.entiy.pojo.Post;
import com.niko.blog.entiy.vo.PostVo;
import com.niko.blog.service.PostService;
import com.niko.blog.mapper.PostMapper;
import com.niko.blog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private RedisUtil redisUtil;

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

    /**
     * 本章热议文章初始化
     */
    @Override
    public void initWeekRank() {

        //获取7天内评论的文章
        List<Post> posts = this.list(new QueryWrapper<Post>()
                .gt("created", DateUtil.offsetDay(new Date(), -7))
                .select("id", "title", "user_id", "comment_count", "view_count", "created")
        );

        //文章总评论量初始化
        for (Post post : posts) {
            String key = "day:rank" + DateUtil.format(post.getCreated(), DatePattern.PURE_DATE_FORMAT);

            redisUtil.zSet(key, post.getId(), post.getCommentCount());

            //设置过期时间
            //七天自动过期 7 - （当前日期-文章日期） = 还能存活的时间
            Long between = DateUtil.between(new Date(), post.getCreated(), DateUnit.DAY);
            long expireTime = (7 - between) * 24 * 60 * 60; //存活时间

            redisUtil.expire(key, expireTime);

            //将文章部分信息做缓存(如：id,标题，评论数量，作者)
            this.hashCachePostIdAndTitle(post, expireTime);

        }
        //做并集
        this.zunionAndStoreLast7DayForWeekRank();

    }

    /**
     * 对有新添加评论的文章进行缓存处理
     * @param postId
     * @param isIncr
     */
    @Override
    public void incrCommentCountAndUnionForWeekRank(long postId, boolean isIncr) {

        String key = "day:rank" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);

        //为文章评论量加一或减一
        redisUtil.zIncrementScore(key,postId,isIncr? 1:-1);

        Post post = this.getById(postId);

        //设置过期时间
        //七天自动过期 7 - （当前日期-文章日期） = 还能存活的时间
        Long between = DateUtil.between(new Date(), post.getCreated(), DateUnit.DAY);
        long expireTime = (7 - between) * 24 * 60 * 60; //存活时间

        //将有新评论添加的文章做缓存处理
        this.hashCachePostIdAndTitle(post,expireTime);

        //再次做并集，获得一周内相同文章的所有评论量
        this.zunionAndStoreLast7DayForWeekRank();


    }

    /**
     * 对文章的观看次数做缓存处理，每次刷新页面或点击文章就会刷新一次
     * @param postVo
     */
    @Override
    public void putViewCount(PostVo postVo) {
        String key = "rank:post:" + postVo.getId();
        //1 从缓存中获取文章观看次数
        Integer viewCount = (Integer) redisUtil.hget(key, "post:viewCount");

        //2 如果没有，从实体类获取加1
        if (viewCount != null){
            postVo.setViewCount(viewCount+1);
        } else {
            postVo.setViewCount(postVo.getViewCount()+1);
        }

        //3 同步到缓存中
        redisUtil.hset(key,"post:viewCount",postVo.getViewCount());

    }

    /**
     * 一周文章的评论总量
     */
    private void zunionAndStoreLast7DayForWeekRank() {
        String key = "day:rank" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);

        String destKey = "week:rank";

        List<String > otherKeys = new ArrayList<>();
        for (int i = -6; i < 0; i++) {
            String otherKey = "day:rank"+DateUtil.format(DateUtil.offsetDay(new Date(), i),DatePattern.PURE_DATE_FORMAT);
            otherKeys.add(otherKey);
        }

        redisUtil.zUnionAndStore(key,otherKeys,destKey);

    }

    /**
     * 将文章部分信息做缓存(id,标题，评论数量，作者)
     *
     * @param post
     */
    private void hashCachePostIdAndTitle(Post post, long expireTime) {
        String key = "rank:post:" + post.getId();
        boolean hasKey = redisUtil.hasKey(key);
        if (!hasKey){
            redisUtil.hset(key,"post:id",post.getId(),expireTime);
            redisUtil.hset(key,"post:title",post.getTitle(),expireTime);
            redisUtil.hset(key,"post:commentCount",post.getCommentCount(),expireTime);
            redisUtil.hset(key,"post:viewCount",post.getViewCount(),expireTime);
        }

    }


}




