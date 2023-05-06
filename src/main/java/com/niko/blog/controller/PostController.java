package com.niko.blog.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.niko.blog.common.lang.Result;
import com.niko.blog.config.RabbitConfig;
import com.niko.blog.entiy.pojo.*;
import com.niko.blog.entiy.vo.CommentVo;
import com.niko.blog.entiy.vo.PostVo;
import com.niko.blog.search.mq.PostMqIndexMessage;
import com.niko.blog.service.PostService;
import com.niko.blog.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.logging.Logger;

@Controller
public class PostController extends BaseController {

    /**
     * 博客分类
     *
     * @param id
     * @return
     */
    @GetMapping("/category/{id:\\d*}")
    public String category(@PathVariable(name = "id") Long id) {
        int pageCurrent = ServletRequestUtils.getIntParameter(req, "pageCurrent", 1);
        req.setAttribute("currentCategoryId", id);
        req.setAttribute("pageCurrent", pageCurrent);
        return "/post/category";
    }

    /**
     * 查找博客的详细内容，并在点击文章详情之后，阅读量加一
     *
     * @param id
     * @return
     */
    @GetMapping("/post/{id:\\d*}")
    public String detail(@PathVariable(name = "id") Long id) {
        //查找博客详细内容
        PostVo postVo = postService.selectOnePost(new QueryWrapper<Post>().eq("p.id", id));
        Assert.notNull(postVo, "文章已被删除");

        //对文章的观看次数做缓存处理，每次刷新页面或点击文章就会刷新一次
        postService.putViewCount(postVo);

        //查找该博客评论列表
        //1分页 2文章id 3用户id 4排序
        IPage<CommentVo> results = commentService.pageVo(getPage(), postVo.getId(), null, "vote_up");

        req.setAttribute("currentCategoryId", postVo.getCategoryId());
        req.setAttribute("currentPostById", postVo);
        req.setAttribute("commentPageData", results);
        return "/post/detail";
    }


    /**
     * 查看当前登录用户是否收藏过点击的这篇文章
     *
     * @param pid
     * @return
     */
    @ResponseBody
    @PostMapping("/collection/find/")
    public Result collectionFind(Long pid) {

        int count = (int) userCollectionService.count(new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, getAccountVoId())
                .eq(UserCollection::getPostId, pid)
        );

        return Result.success(MapUtil.of("collection", count > 0));
    }

    /**
     * 收藏文章
     *
     * @param pid
     * @return
     */
    @ResponseBody
    @PostMapping("/collection/add/")
    public Result collectionAdd(Long pid) {

        Post post = postService.getById(pid);
        Assert.isTrue(post != null, "该帖子已被删除");

        int count = (int) userCollectionService.count(new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, getAccountVoId())
                .eq(UserCollection::getPostId, pid)
        );

        /*if (count > 0) {
            return Result.fail("你已收藏该贴");
        }*/

        UserCollection userCollection = new UserCollection();
        userCollection.setUserId(getAccountVoId());
        userCollection.setPostId(pid);
        userCollection.setPostUserId(post.getUserId());
        userCollection.setCreated(new Date());
        userCollection.setModified(new Date());

        userCollectionService.save(userCollection);

        return Result.success();
    }

    /**
     * 取消对此文章的收藏
     *
     * @param pid
     * @return
     */
    @ResponseBody
    @PostMapping("/collection/remove/")
    public Result collectionRemove(Long pid) {
        Post post = postService.getById(pid);
        Assert.isTrue(post != null, "该帖子已被删除");

        userCollectionService.remove(new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, getAccountVoId())
                .eq(UserCollection::getPostId, pid)
        );
        return Result.success();
    }

    /**
     * 进入发布帖子或编辑帖子的页面
     *
     * @return
     */
    @GetMapping("/post/edit")
    public String edit() {

        String pid = req.getParameter("id");
        if (!StringUtils.isEmpty(pid)) {
            Post post = postService.getById(pid);
            Assert.isTrue(post != null, "该帖子已被删除");
            Assert.isTrue(post.getUserId().longValue() == getAccountVoId().longValue(), "没有权限操作此文章");
            req.setAttribute("post", post);
        }
        req.setAttribute("categories", categoryService.list());

        return "/post/edit";
    }

    /**
     * 提交发布的文章
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/post/submit")
    public Result submit(Post post) {
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(post);
        if (validResult.hasErrors()) {
            return Result.fail(validResult.getErrors());
        }

        if (post.getId() == null) {
            post.setUserId(getAccountVoId());

            post.setModified(new Date());
            post.setCreated(new Date());
            post.setCommentCount(0);
            post.setEditMode(null);
            post.setLevel(0);
            post.setRecommend(0);
            post.setViewCount(0);
            post.setVoteDown(0);
            post.setVoteUp(0);
            postService.save(post);

        } else {
            Post tempPost = postService.getById(post.getId());
            Assert.isTrue(tempPost.getUserId().longValue() == getAccountVoId().longValue(), "无权限编辑此文章！");

            tempPost.setTitle(post.getTitle());
            tempPost.setContent(post.getContent());
            tempPost.setCategoryId(post.getCategoryId());
            postService.updateById(tempPost);
        }

        // 通知消息给mq，告知更新或添加到es
        amqpTemplate.convertAndSend(RabbitConfig.ES_EXCHANGE,RabbitConfig.ES_BIND_KEY,
                new PostMqIndexMessage(post.getId(),PostMqIndexMessage.CREATE_OR_UPDATE));


        return Result.success().action("/post/" + post.getId());
    }

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    @ResponseBody
    @Transactional
    @PostMapping("/post/delete")
    public Result delete(Long id) {
        Post post = postService.getById(id);

        Assert.notNull(post, "该帖子已被删除");
        Assert.isTrue(post.getUserId().longValue() == getAccountVoId().longValue(), "无权限删除此文章！");

        boolean b = postService.removeById(id);
        if (b) {
            // 删除相关消息、收藏，评论等
            userMessageService.removeByMap(MapUtil.of("post_id", id)); //相关消息
            userCollectionService.removeByMap(MapUtil.of("post_id", id)); //收藏
            commentService.removeByMap(MapUtil.of("post_id", id)); //评论

            //用户信息更新
            User user = userService.getById(getAccountVoId());
            user.setCommentCount((int) commentService.count(new LambdaQueryWrapper<Comment>().eq(Comment::getUserId, getAccountVoId())));
            user.setPostCount((user.getPostCount() - 1) < 0 ? 0 : user.getPostCount() - 1);
            user.setModified(new Date());
            userService.updateById(user);

            // 通知消息给mq，告知更新或添加到es
            amqpTemplate.convertAndSend(RabbitConfig.ES_EXCHANGE,RabbitConfig.ES_BIND_KEY,
                    new PostMqIndexMessage(post.getId(),PostMqIndexMessage.REMOVE));


        }

        return Result.success().action("/user/index");
    }

    /**
     * 保存对文章的新评论
     *
     * @param pid
     * @param content
     * @return
     */
    @ResponseBody
    @Transactional
    @PostMapping("/post/reply")
    public Result reply(Long pid, String content) {

        Assert.notNull(pid, "找不到对应的文章");
        Assert.hasLength(content, "评论不能为空");

        Post post = postService.getById(pid);
        Assert.isTrue(post != null, "该文章已被删除");

        Comment comment = new Comment();
        comment.setPostId(pid);
        comment.setContent(content);
        comment.setUserId(getAccountVoId());
        comment.setCreated(new Date());
        comment.setModified(new Date());
        comment.setLevel(0);
        comment.setVoteDown(0);
        comment.setVoteUp(0);
        commentService.save(comment);

        // 评论数量加一
        post.setCommentCount(post.getCommentCount() + 1);
        postService.updateById(post);

        // 本周热议数量加一
        postService.incrCommentCountAndUnionForWeekRank(post.getId(), true);

        //异步通知作者有人评论了你的文章
        if (comment.getUserId().longValue() != post.getUserId().longValue()) {
            //判断文章是不是登陆者，登陆者自己的文章不需要通知
            allAsync.tellAuthorMessage(post,comment, getAccountVoId(),null,1);
        }

        //异步通知被@的人，有人回复了你的评论
        if (content.startsWith("@")) {
            String username = content.substring(1,content.indexOf(" "));
            User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
            if(user != null) {
                allAsync.tellAuthorMessage(post,comment,getAccountVoId(),user.getId(),2);
            }
        }
        //异步跟新user表信息
        allAsync.updateUser();

        return Result.success().action("/post/" + pid);
    }

    @ResponseBody
    @Transactional
    @PostMapping("/post/commentDelete/")
    public Result reply(Long id) {

        Assert.notNull(id, "评论id不能为空！");

        Comment comment = commentService.getById(id);

        Assert.notNull(comment, "找不到对应评论！");

        if(comment.getUserId().longValue() != getAccountVoId().longValue()) {
            if (!"admin".equals(getAccountVo().getUsername())){
                return Result.fail("不是你发表的评论！");
            }
        }
        //删除评论表的信息
        commentService.removeById(id);
        //删除userMessage表的信息
        userMessageService.remove(new LambdaQueryWrapper<UserMessage>().eq(UserMessage::getCommentId,id));

        // 评论数量减一
        Post post = postService.getById(comment.getPostId());
        post.setCommentCount(post.getCommentCount() - 1);
        postService.saveOrUpdate(post);

        //异步更新用户信息
        allAsync.updateUser();

        //评论数量减一
        postService.incrCommentCountAndUnionForWeekRank(comment.getPostId(), false);

        return Result.success(null);
    }


}
