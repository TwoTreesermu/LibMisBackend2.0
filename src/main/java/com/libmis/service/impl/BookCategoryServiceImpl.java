package com.libmis.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.BookCategory;
import com.libmis.mapper.BookCategoryMapper;
import com.libmis.service.BookCategoryService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory>
        implements BookCategoryService {

}
