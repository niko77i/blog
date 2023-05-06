package com.niko.blog.entiy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicTotalAndCollectionTotal implements Serializable {

    /**
     * 发布文章总数
     */
    private Long publicTotal;

    /**
     * 收藏文章总数
     */
    private Long collectionTotal;
}
