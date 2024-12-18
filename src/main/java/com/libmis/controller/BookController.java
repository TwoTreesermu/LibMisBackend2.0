package com.libmis.controller;

import com.libmis.entity.Book;
import com.libmis.service.BookService;
import com.libmis.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    // 前端以json格式发送数据，需要加@RequestBody
    // 以表单形式提交，则不需要@RequestBody

    /**
     * 查询所有图书信息(没有考虑分页查询)
     * @return 所有图书信息，封账在Result 的 data 中
     */
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

    /**
     * 更新图书信息
     * @param book  前端发送来的 book 实例
     * @return 一个不带数据的 Result
     */
    @PutMapping("/update")
    public Result update(@RequestBody Book book) {

        //todo

        return Result.success();
    }

    /**
     * 添加图书信息
     * @param book 前端发送来的 book 实例
     * @return 一个不带数据的 Result
     */
    @PostMapping("/save")
    public Result save(@RequestBody Book book) {

        // todo

        return Result.success();
    }

    /**
     * 删除一本图书信息
     * @param bookId  前端发来的bookId
     * @return  一个不带数据的 Result,
     */
    @DeleteMapping("/del/{bookId}")
    public Result del(@PathVariable Integer bookId) {
        // todo
        return Result.success();
    }

    /**
     * 根据bookId 查询图书信息
     * @param bookId 前端发来的bookId
     * @return  查询的结果, 一个book实例
     */
    @GetMapping("/find{bookId}")
    public Result find(@PathVariable Integer bookId) {

        Book book = bookService.getById(bookId);
        return Result.success(book);
    }

    /**
     * 分页查询显示图书信息
     * @param page 当前页数
     * @param pageSize 每页的图书信息数
     * @return 对应页的图书信息
     */
    @GetMapping("booksByPage")
    public Result<?> booksListByPage(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "5") Integer pageSize) {
        // todo

        return null;
    }

}
