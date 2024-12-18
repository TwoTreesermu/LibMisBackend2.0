package com.libmis;

import com.libmis.entity.Book;
import com.libmis.service.impl.BookServiceImpl;
import com.libmis.utils.Result;
import org.junit.jupiter.api.Test;
import com.libmis.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MpMethodsTest {
//    @Test
    public void testMpMethods() {
        BookService bookService = new BookServiceImpl();
        List<Book> booksList = bookService.list();
        System.out.println(Result.success(booksList));
    }

    @Test
    public void del() {
        // 联表删除的问题
        // id不存在时不报错的问题
        try {
            BookService bookService = new BookServiceImpl();
            log.info("checkPoint1");
        }catch (Exception e){
            log.info("checkPoint2");
            log.error(e.getMessage());
        }
    }
}
