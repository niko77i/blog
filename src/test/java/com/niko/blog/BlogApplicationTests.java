package com.niko.blog;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.niko.blog.entiy.pojo.Category;
import com.niko.blog.entiy.pojo.Comment;
import com.niko.blog.entiy.vo.AskMess;
import com.niko.blog.search.model.PostDocument;
import com.niko.blog.search.repository.PostRepository;
import com.niko.blog.service.CategoryService;
import com.niko.blog.service.CommentService;
import com.niko.blog.service.SearchService;
import com.niko.blog.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    CategoryService categoryService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CommentService commentService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    SearchService searchService;

    @Test
    void contextLoads() throws IOException {
//        PostDocument postDocument = new PostDocument();
//        postDocument.setId(1L);
//        postDocument.setAuthorId(2L);
//        postDocument.setCreated(new Date());
//        postDocument.setAuthorAvatar("asda");
//        postDocument.setCommentCount(2);
//        postDocument.setLevel(1);
//        postDocument.setTitle("nini");
//        postDocument.setCategoryId(3L);
//        postDocument.setViewCount(4);
//        postDocument.setAuthorName("lili");
//        postDocument.setRecomment(true);
//        postDocument.setCategoryName("ma");
//
//        postRepository.save(postDocument);
        postRepository.deleteById(1L);


    }

}
