package com.niko.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niko.blog.async.AllAsync;
import com.niko.blog.entiy.vo.AccountVo;
import com.niko.blog.service.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.amqp.core.AmqpTemplate;
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

    @Autowired
    UserService userService;

    @Autowired
    UserCollectionService userCollectionService;

    @Autowired
    UserMessageService userMessageService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AllAsync allAsync;

    @Autowired
    SearchService searchService;

    @Autowired
    AmqpTemplate amqpTemplate;

    public Page getPage() {
        int pageCurrent = ServletRequestUtils.getIntParameter(req, "pageCurrent", 1);
        int pageSize = ServletRequestUtils.getIntParameter(req, "pageSize", 10);

        return new Page(pageCurrent, pageSize);
    }

    protected AccountVo getAccountVo() {
        return (AccountVo) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getAccountVoId() {
        return getAccountVo().getId();
    }
}
