package com.libmis.controller;

import com.libmis.entity.*;
import com.libmis.service.*;
import com.libmis.utils.Jwt;
import com.libmis.utils.PageQuery;
import com.libmis.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Created by 二木
 * @Coded by Ywqtttu
 */
@Slf4j
@RestController
@RequestMapping("/books") // 具体url待定
public class BookController {
    @Autowired
    private BookService bookService; // 注入 bookService
    @Autowired
    private PageQuery pageQuery;
    @Autowired
    private BookStorageService bookStorageService;
    @Autowired
    private ReservationRecordService reservationRecordService;
    @Autowired
    private ReservationRecord reservationRecord;
    @Autowired
    private BorrowRecordService borrowRecordService;
    @Autowired
    private BorrowRecord br;
    @Autowired
    private UserService userService;
    @Autowired
    private OperationLogService operationLogService;

    // 前端以json格式发送数据，需要加@RequestBody
    // 以表单形式提交，则不需要@RequestBody
    @GetMapping("/testTxt")
    public Result<?> testTxt() {
        return Result.success("testInfo by ywqtttu.");
    }

    /**
     * 查询所有图书信息(没有考虑分页查询)
     *
     * @return 所有图书信息，封装在Result 的 data 中
     */
    @GetMapping("/booksList")
    public Result<?> listBooks() {
        try {
            List<Book> booksList = bookService.list();
            log.info("booksList ={}", booksList);
            return Result.success(booksList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 更新图书信息
     *
     * @param book 前端发送来的 book 实例
     * @return 一个不带数据的 Result
     */
    @PutMapping("/update")
    public Result update(@RequestBody Book book) {
        try {
            bookService.saveOrUpdate(book); // 假如没有就新建一个
            return Result.success("更新书籍信息成功了喵。");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 添加图书信息
     *
     * @param book 前端发送来的 book 实例
     * @return 一个不带数据的 Result
     */
    @PostMapping("/save")
    public Result save(@RequestBody Book book) {
        try {
            bookService.save(book);
            return Result.success("保存书籍信息成功了喵。");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 删除一本图书信息
     *
     * @param bookId 前端发来的 bookId
     * @return 一个不带数据的 Result,
     */
    @DeleteMapping("/del/{bookId}")
    public Result del(@PathVariable int bookId) {
        // 联表删除的问题
        // *已解决* id不存在时不报错的问题
        try {
            if (bookService.removeById(bookId)) {
                return Result.success("删除成功了喵。");
            } else {
                return Result.error("501", "哈麻皮你输的bookId不对。");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 根据bookId 查询图书信息
     * @param bookId 前端发来的bookId
     * @return 查询的结果, 一个book实例
     */
    @GetMapping("/find/{bookId}")
    public Result find(@PathVariable Integer bookId) {
        try {
            Book book = bookService.getById(bookId);
            return Result.success(book);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 分页显示图书信息
     * @param pageNum 当前页数
     * @param pageSize 每页的图书记录数
     * @return 对应页的图书信息
     */
    @GetMapping("/booksByPage")
    public Result<?> bookListByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "5")Integer pageSize
                                    ) {
        try {
            return pageQuery.pageQuery("book", pageNum, pageSize, null, null);
        }catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     *查询、检索, 分页显示图书信息
     * @param pageNum 当前页数
     * @param pageSize 每页的图书记录数
     * @param search  查询关键词
     * @return 对应页的图书信息
     */
    @GetMapping("/BySearchPage")
    public Result<?> booksListByConditionPage(@RequestParam(defaultValue = "book")String type,
                                              @RequestParam(defaultValue = "1")Integer pageNum,
                                              @RequestParam(defaultValue = "5")Integer pageSize,
                                              @RequestParam(defaultValue = "")String search) {
        try {
            return pageQuery.pageQuery(type, pageNum, pageSize, "title", search);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }


}