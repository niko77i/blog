package com.niko.blog.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.niko.blog.template.HotsTemplate;
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

    @Autowired
    private HotsTemplate hotsTemplate;

    @PostConstruct
    public void setUp() {
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("posts", postsTemplate);
        configuration.setSharedVariable("hots", hotsTemplate);
        configuration.setSharedVariable("shiro", new ShiroTags());
    }

}
