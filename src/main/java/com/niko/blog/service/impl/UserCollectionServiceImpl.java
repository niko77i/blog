package com.niko.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niko.blog.entiy.pojo.UserCollection;
import com.niko.blog.service.UserCollectionService;
import com.niko.blog.mapper.UserCollectionMapper;
import org.springframework.stereotype.Service;

/**
* @author 阳
* @description 针对表【user_collection】的数据库操作Service实现
* @createDate 2023-01-05 14:37:39
*/
@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection>
    implements UserCollectionService{

}




