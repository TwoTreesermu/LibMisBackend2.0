package com.libmis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.BookStorage;
import com.libmis.mapper.BookStorageMapper;
import com.libmis.service.BookStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookStorageServiceImpl extends ServiceImpl<BookStorageMapper, BookStorage>
        implements BookStorageService {
    
    @Autowired
    private BookStorageMapper bookStorageMapper;
    
    @Override
    public void returnBook(int bookId){
        BookStorage bookStorage = bookStorageMapper.selectById(bookId);
        bookStorage.setRealQuantity(bookStorage.getRealQuantity() + 1);
        bookStorageMapper.updateById(bookStorage);
    }
}
