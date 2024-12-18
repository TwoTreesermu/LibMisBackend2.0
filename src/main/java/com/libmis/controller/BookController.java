package com.libmis.controller;

import com.libmis.entity.Book;
import com.libmis.service.BookService;
import com.libmis.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author 二木
 * @date 2024-12-17 22:15
 */

@Slf4j
@RestController
@RequestMapping("/testUrl") // 具体url待定
public class BookController {

    @Autowired
    private BookService bookService; // 注入 bookService

    @GetMapping("/booksList")
    public Result<?> booksList() {
        List<Book> list = bookService.list();
        log.info("booksList ={}", list);
        return Result.success(list);
//        return Result.success(null);
    }
    @GetMapping("/testTxt")
    public Result<?> testTxt() {
        return Result.success("testInfo by ywqtttu.");
    }
}
