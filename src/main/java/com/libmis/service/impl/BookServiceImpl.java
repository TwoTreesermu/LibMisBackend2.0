package com.libmis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.Book;
import com.libmis.entity.BookStorage;
import com.libmis.mapper.BookMapper;
import com.libmis.mapper.BookStorageMapper;
import com.libmis.service.BookService;
import com.libmis.service.BookStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二木
 * @date 2024-12-17 22:07
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
        implements BookService {
    @Autowired
    BookStorageService bookStorageService;
    @Autowired
    BookStorageMapper bookStorageMapper;

    @Override
    public boolean checkBookStorage(int bookId, int userId){
        BookStorage bookStorage = bookStorageService.getById(bookId);
        if (bookStorage.getRealQuantity()> 0 && bookStorageMapper.checkBorrowed(bookId, userId) == null){
            bookStorage.setRealQuantity(bookStorage.getRealQuantity()-1);
            bookStorageService.updateById(bookStorage);
            return true;
        }
        else{
            return false;
        }
    }
}
