package com.niko.blog.template;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niko.blog.common.templates.DirectiveHandler;
import com.niko.blog.common.templates.TemplateDirective;
import com.niko.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostsTemplate extends TemplateDirective {

    @Autowired
    PostService postService;

    @Override
    public String getName() {
        return "posts";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        Integer level = handler.getInteger("level");
        Integer pageCurrent = handler.getInteger("pageCurrent", 1);
        Integer pageSize = handler.getInteger("pageSize", 2);
        Long categoryId = handler.getLong("categoryId");

        IPage page = postService.pageVo(new Page(pageCurrent, pageSize), categoryId, null, level, null, "created");

        handler.put(RESULTS, page).render();
    }
}
