package com.niko.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niko.blog.entiy.pojo.Category;
import com.niko.blog.service.CategoryService;
import com.niko.blog.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author 阳
* @description 针对表【category】的数据库操作Service实现
* @createDate 2023-01-05 14:18:34
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




