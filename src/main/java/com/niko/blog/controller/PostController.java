package com.niko.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.niko.blog.entiy.pojo.Post;
import com.niko.blog.entiy.vo.CommentVo;
import com.niko.blog.entiy.vo.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class PostController extends BaseController{


    @GetMapping("/category/{id:\\d*}")
    public String category(@PathVariable(name = "id")Long id){
        int pageCurrent = ServletRequestUtils.getIntParameter(req,"pageCurrent",1);
        req.setAttribute("currentCategoryId",id);
        req.setAttribute("pageCurrent",pageCurrent);
        return "/post/category";
    }

    @GetMapping("/post/{id:\\d*}")
    public String detail(@PathVariable(name = "id")Long id){
        //查找博客详细内容
        PostVo postVo = postService.selectOnePost(new QueryWrapper<Post>().eq("p.id",id));
        Assert.notNull(postVo,"文章已被删除");

        //查找该博客评论列表
        //1分页 2文章id 3用户id 4排序
        IPage<CommentVo> results = commentService.pageVo(getPage(),postVo.getId(),null,"vote_up");

        req.setAttribute("currentCategoryId",postVo.getCategoryId());
        req.setAttribute("currentPostById",postVo);
        req.setAttribute("commentPageData",results);
        return "/post/detail";
    }


}
