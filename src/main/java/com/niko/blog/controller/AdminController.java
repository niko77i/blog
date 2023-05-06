package com.niko.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niko.blog.common.lang.Result;
import com.niko.blog.entiy.pojo.Post;
import com.niko.blog.entiy.vo.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    /**
     *
     * @param id
     * @param rank 1 表示置顶或精帖
     * @param field
     * @return
     */
    @ResponseBody
    @PostMapping("/jie-set/")
    public Result jetSet(Long id, Integer rank, String field) {

        Post post = postService.getById(id);
        Assert.notNull(post, "该帖子已被删除");

        if ("delete".equals(field)) {
            postService.removeById(id);
            //异步更新用户信息
            allAsync.updateUser();
            return Result.success();

        } else if ("status".equals(field)) {
            post.setRecommend(rank);
            post.setModified(new Date());

        } else if ("stick".equals(field)) {
            post.setLevel(rank);
            post.setModified(new Date());
        }
        postService.updateById(post);
        return Result.success();
    }

    @ResponseBody
    @RequestMapping("/initEsData")
    public Result initEsData(){
        int size = 10000;
        Page page = new Page();
        page.setSize(size);

        long total = 0; //初始化到es中的数据总条数

        for (int i = 0; i < 10; i++) {
            page.setCurrent(i);

            IPage<PostVo> postVoIPage = postService.pageVo(page, null, null, null, null, null);

            int num = searchService.initEsData(postVoIPage.getRecords());

            total += num;

            // 当一页查不出10000条的时候，说明是最后一页了
            if(postVoIPage.getRecords().size() < size) {
                break;
            }
        }
        return Result.success("ES索引初始化成功，共 " + total + " 条记录！",null);
    }
}
