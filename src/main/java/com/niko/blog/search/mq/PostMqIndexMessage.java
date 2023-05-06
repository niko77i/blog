package com.niko.blog.search.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMqIndexMessage  implements Serializable {

    //两种type
    public final static String CREATE_OR_UPDATE="create_update";
    public final static String REMOVE="remove";

    private Long postId;

    //操作类型
    private String type;
}
