package com.libmis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.Book;
import com.libmis.mapper.BookMapper;
import com.libmis.service.BookService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二木
 * @date 2024-12-17 22:07
 */
@Component
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
        implements BookService {


}
