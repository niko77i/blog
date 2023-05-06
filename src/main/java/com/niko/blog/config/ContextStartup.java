package com.niko.blog.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.niko.blog.entiy.pojo.Category;
import com.niko.blog.service.CategoryService;
import com.niko.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * 启动时注入
 */
@Component
public class ContextStartup implements ApplicationRunner, ServletContextAware {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;


    private ServletContext servletContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //初始化主页展示文章信息
        List<Category> categories = categoryService.list(new LambdaQueryWrapper<Category>().eq(Category::getStatus, 0));
//        System.out.println(categories);
        servletContext.setAttribute("categories",categories);

        //初始化本周的热议文章
        postService.initWeekRank();

    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext=servletContext;
    }
}
