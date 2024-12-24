package com.libmis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libmis.entity.Book;
import org.springframework.stereotype.Service;

/**
 * @author 二木
 * @date 2024-12-17 22:06
 */
@Service
public interface BookService extends IService<Book> {
    // 如果有其他的需求，可以在该接口声明方法，然后在对应的实现类进行实现
    boolean checkBookStorage(int bookId, int userId);
}
