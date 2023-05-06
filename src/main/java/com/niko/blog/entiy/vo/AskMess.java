package com.niko.blog.entiy.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AskMess implements Serializable {

    private Long postId;

    private String comment;

    private String title;

    private Date created;
}
