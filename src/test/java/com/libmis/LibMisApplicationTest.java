package com.libmis;

import com.libmis.entity.Book;
import com.libmis.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 二木
 * @date 2024-12-17 21:49
 * 测试类，用于调试代码
 */
@SpringBootTest
public class LibMisApplicationTest {

    @Resource
    private BookMapper bookMapper;

    @Resource
    private BookService bookService;



    @Test
    public void testBookMapper() {
        System.out.println("bookMapper = " + bookMapper.getClass());
        Book book = bookMapper.selectById(5);
        System.out.println("book = " + book);
    }

    @Test
    public void testBookServe() {
        List<Book> list = bookService.list();
        for (Book book : list) {
            System.out.println(book);
        }
    }

    // 增加了一行注释
}