package com.niko.blog.config;

import com.niko.blog.template.PostsTemplate;
import com.niko.blog.template.TimeAgoMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class FreemarkerConfig {

    @Resource
    private freemarker.template.Configuration configuration;

    @Autowired
    private PostsTemplate postsTemplate;

    @PostConstruct
    public void setUp() {
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("posts", postsTemplate);
    }

}
