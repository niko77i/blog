package com.niko.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niko.blog.service.CommentService;
import com.niko.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {
    @Autowired
    HttpServletRequest req;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    public Page getPage(){
        int pageCurrent = ServletRequestUtils.getIntParameter(req,"pageCurrent",1);
        int pageSize = ServletRequestUtils.getIntParameter(req,"pageSize",10);

        return new Page(pageCurrent,pageSize);
    }
}
