package com.niko.blog.schedules;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.niko.blog.entiy.pojo.Post;
import com.niko.blog.service.PostService;
import com.niko.blog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ViewCountAsyTask {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PostService postService;

    @Scheduled(cron = "0 0/1 * * * *") //每分钟执行一次
    public void task(){
        Set<String> keys = redisTemplate.keys("rank:post:*");

        List<String> ids = new ArrayList<>();

        //取出缓存中 文章存在阅读量的id
        for (String key:keys){
            if (redisUtil.hHasKey(key,"post:viewCount")) {
                ids.add(key.substring("rank:post".length()+1));
            }
        }

        if (ids.isEmpty()) return;

        //更新阅读量
        List<Post> posts = postService.list(new QueryWrapper<Post>().in("id", ids));
        posts.stream().forEach((post) -> {
            Integer viewCount = (Integer) redisUtil.hget("rank:post:" + post.getId(), "post:viewCount");
            post.setViewCount(viewCount);
        });

        if (posts.isEmpty()) return;

        boolean isSuccess = postService.updateBatchById(posts);

        //同步成功删除缓存
        if (isSuccess){
            ids.stream().forEach((id) ->{
                redisUtil.hdel("rank:post:" + id , "post:viewCount");
                System.out.println(id+"--------->阅读量同步成功");
            });
        }
    }


}
