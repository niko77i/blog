package com.niko.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niko.blog.entiy.vo.PostVo;
import com.niko.blog.search.mq.PostMqIndexMessage;

import java.util.List;

public interface SearchService {
    /**
     * es搜索
     * @param page
     * @param keyWord
     * @return
     */
    IPage search(Page page, String keyWord);

    /**
     * 初始化es数据
     * @param records
     * @return
     */
    int initEsData(List<PostVo> records);

    /**
     * mq发来的消息，对es上的数据修改
     * @param message
     */
    void createOrUpdate(PostMqIndexMessage message);

    /**
     * mq发来的消息，对es上的数据删除
     * @param message
     */
    void removeIndex(PostMqIndexMessage message);
}
