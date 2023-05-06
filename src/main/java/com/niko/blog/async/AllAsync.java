package com.niko.blog.async;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mchange.lang.LongUtils;
import com.niko.blog.entiy.pojo.Comment;
import com.niko.blog.entiy.pojo.Post;
import com.niko.blog.entiy.pojo.User;
import com.niko.blog.entiy.pojo.UserMessage;
import com.niko.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class AllAsync {

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    PostService postService;

    @Autowired
    UserMessageService userMessageService;

    @Autowired
    WebSocketService webSocketService;

    /**
     * 更新user表信息
     */
    @Async
    @Transactional
    public void updateUser() {
        for (User user : userService.list()) {
            user.setCommentCount((int) commentService.count(new LambdaQueryWrapper<Comment>().eq(Comment::getUserId, user.getId())));
            user.setModified(new Date());
            user.setPostCount((int) postService.count(new LambdaQueryWrapper<Post>().eq(Post::getUserId, user.getId())));
            userService.updateById(user);
        }
    }

    /**
     * 异步更新user-message表,更新成功通过websocket通知对方
     *
     * @param post       评论的文章
     * @param fromUserId 评论者的id
     * @param toUserId 被评论者的id
     * @param type       消息类型
     */
    @Async
    public void tellAuthorMessage(Post post, Comment comment, Long fromUserId, Long toUserId, Integer type) {
        UserMessage userMessage = new UserMessage();
        userMessage.setPostId(post.getId());
        userMessage.setContent(comment.getContent());
        userMessage.setCommentId(comment.getId());
        userMessage.setFromUserId(fromUserId);
        userMessage.setToUserId(toUserId == null ? post.getUserId() : toUserId);
        userMessage.setType(type); //1表示评论文章信息,2表示消息被评论
        userMessage.setStatus(0);
        userMessage.setCreated(new Date());
        userMessage.setModified(new Date());
        boolean save = userMessageService.save(userMessage);
        if (save) {
            /**
             * 即时通知,toUserId为null表示评论的文章，通知文章作者
             *          toUserId不为null表示评论的别人的回复，通知此人
             */
            webSocketService.sendMessCountToUser(toUserId == null ? post.getUserId() : toUserId);
        }
    }


}
