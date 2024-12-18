package com.libmis.controller;

import com.libmis.entity.Book;
import com.libmis.service.BookService;
import com.libmis.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 二木
 * @date 2024-12-17 22:15
 */
@Slf4j
@RestController
public class BookController {

    @Resource
    private BookService bookService; // 注入 bookService

    @GetMapping("/booksList")
    public Result<?> booksList() {
        List<Book> list = bookService.list();
        log.info("booksList = ", list);
        return Result.success(list);
    }
}
