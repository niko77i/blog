package com.niko.blog.entiy.vo;

import com.niko.blog.entiy.pojo.UserMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMessageVo extends UserMessage {
    /**
     * 被评论人的名字
     */
    private String toUserName;
    /**
     * 评论人的名字
     */
    private String fromUserName;
    /**
     * 我的评论
     */
    private String myContent;
    /**
     * 评论的文章名称
     */
    private String postTitle;
    /**
     * 评论的内容
     */
    private String commentContent;
}
