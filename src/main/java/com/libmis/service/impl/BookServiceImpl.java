package com.libmis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.Book;
import com.libmis.mapper.BookMapper;
import com.libmis.service.BookService;
import org.springframework.stereotype.Service;


/**
 * @author 二木
 * @date 2024-12-17 22:07
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
        implements BookService {

}
