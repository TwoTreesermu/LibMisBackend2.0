package com.libmis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.BookStorage;
import com.libmis.mapper.BookStorageMapper;
import com.libmis.service.BookStorageService;
import org.springframework.stereotype.Service;


@Service
public class BookStorageServiceImpl extends ServiceImpl<BookStorageMapper, BookStorage>
        implements BookStorageService {


}
