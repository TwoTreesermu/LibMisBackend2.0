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
    @Override
    public boolean checkBookStorage(Book book){
        int bookId = book.getBookId();
        BookStorage bs = bookStorageService.getById(bookId);
        if (bs.getRealQuantity()> 0){
            bs.setRealQuantity(bs.getRealQuantity()-1);
            bookStorageService.updateById(bs);
            return true;
        }
        else{
            return false;
        }
    }
}
