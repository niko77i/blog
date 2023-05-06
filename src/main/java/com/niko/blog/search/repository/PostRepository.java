package com.niko.blog.search.repository;

import com.niko.blog.search.model.PostDocument;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends ElasticsearchRepository<PostDocument, Long> {

    //自己定义


    //符合jpa命名规范的接口

}
