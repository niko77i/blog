package com.niko.blog.entiy.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.niko.blog.entiy.pojo.Comment;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentVo extends Comment implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    private Long authorId;
    private String authorName;
    private String authorAvatar;
}
