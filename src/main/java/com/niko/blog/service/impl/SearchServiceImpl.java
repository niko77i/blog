package com.niko.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.niko.blog.entiy.pojo.Post;
import com.niko.blog.entiy.vo.PostVo;
import com.niko.blog.search.model.PostDocument;
import com.niko.blog.search.mq.PostMqIndexMessage;
import com.niko.blog.search.repository.PostRepository;
import com.niko.blog.service.PostService;
import com.niko.blog.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /**
     * es搜索
     * @param page
     * @param keyWord
     * @return
     */
    @Override
    public IPage search(Page page, String keyWord) {
        //注：es用的jpa的page实现分页
        Long current = page.getCurrent() - 1;
        Long size = page.getSize();

        //分页信息mybatisplus的page转为jpa的page
        Pageable pageable = PageRequest.of(current.intValue(),size.intValue());


        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("title", keyWord))
                .should(QueryBuilders.matchQuery("authorName", keyWord))
                .should(QueryBuilders.matchQuery("categoryName", keyWord));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageable)
                .build();

        SearchHits<PostDocument> search = elasticsearchOperations.search(searchQuery, PostDocument.class);

        List<SearchHit<PostDocument>> searchHits = search.getSearchHits();

        //转为PostDocuments的list
        List<PostDocument> postDocuments = new ArrayList<>();
        for (SearchHit<PostDocument> searchHit : searchHits) {
            postDocuments.add(searchHit.getContent());
        }

        //jpa的page转成mybatisplus的page
        Page pageData = new Page(page.getCurrent(), page.getSize(), search.getTotalHits());
        pageData.setRecords(postDocuments);

        return pageData;
    }

    /**
     * 初始化es数据
     * @param records
     * @return
     */
    @Override
    public int initEsData(List<PostVo> records) {

        if(records == null || records.isEmpty()) {
            return 0;
        }

        List<PostDocument> documents = new ArrayList<>();
        for(PostVo vo : records) {
            // 映射转换
            PostDocument postDocument = new PostDocument();
            BeanUtils.copyProperties(vo,postDocument);
            documents.add(postDocument);
        }
        postRepository.saveAll(documents);
        return documents.size();

    }

    /**
     * mq发来的消息，对es上的数据修改
     * @param message
     */
    @Override
    public void createOrUpdate(PostMqIndexMessage message) {
        Long postId = message.getPostId();
        PostVo postVo = postService.selectOnePost(new QueryWrapper<Post>().eq("p.id", postId));

        PostDocument postDocument = new PostDocument();
        BeanUtils.copyProperties(postVo,postDocument);

        postRepository.save(postDocument);

        log.info("es 索引更新成功！ --> {}",postDocument.toString());
    }

    /**
     * mq发来的消息，对es上的数据删除
     * @param message
     */
    @Override
    public void removeIndex(PostMqIndexMessage message) {

        Long postId = message.getPostId();
        postRepository.deleteById(postId);

        log.info("es 索引删除成功! --> {}",message.toString());

    }
}
