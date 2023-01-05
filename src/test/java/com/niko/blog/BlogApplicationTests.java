package com.niko.blog;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.niko.blog.entiy.pojo.Category;
import com.niko.blog.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    CategoryService categoryService;
    @Test
    void contextLoads() {
        List<Category> categories = categoryService.list(new LambdaQueryWrapper<Category>().eq(Category::getStatus, 0));
        System.out.println(categories);
    }

}
