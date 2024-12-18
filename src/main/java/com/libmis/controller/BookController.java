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
import java.util.List;

/**
 * @author 二木
 * @date 2024-12-17 22:15
 */
@Slf4j
@RestController
@RequestMapping("/books") // 具体url待定
public class BookController {

    @Autowired
    private BookService bookService; // 注入 bookService

    // 前端以json格式发送数据，需要加@RequestBody
    // 以表单形式提交，则不需要@RequestBody
    @GetMapping("/testTxt")
    public Result<?> testTxt() {
        return Result.success("testInfo by ywqtttu.");
    }

    /**
     * 查询所有图书信息(没有考虑分页查询)
     * @return 所有图书信息，封装在Result 的 data 中
     */
    @GetMapping("/booksList")
    public Result<?> listBooks() {
        try{
        List<Book> booksList = bookService.list();
        log.info("booksList ={}", booksList);
        return Result.success(booksList);
        } catch(Exception e){
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 更新图书信息
     * @param book  前端发送来的 book 实例
     * @return 一个不带数据的 Result
     */
    @PutMapping("/update")
    public Result update(@RequestBody Book book) {
        try{
        bookService.saveOrUpdate(book); // 假如没有就新建一个
        return Result.success();
        } catch(Exception e){
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 添加图书信息
     * @param book 前端发送来的 book 实例
     * @return 一个不带数据的 Result
     */
    @PostMapping("/save")
    public Result save(@RequestBody Book book) {
        // todo
        try{
        bookService.save(book);
        return Result.success();
        } catch(Exception e){
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 删除一本图书信息
     * @param bookId  前端发来的bookId
     * @return  一个不带数据的 Result,
     */
    @DeleteMapping("/del/{bookId}")
    public Result del(@PathVariable int bookId) {
        // 联表删除的问题
        // *已解决* id不存在时不报错的问题
        try {
            if (bookService.removeById(bookId)){
                return Result.success();
            }else{
                return Result.error("501", "哈麻皮你输的bookId不对。");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 根据bookId 查询图书信息
     * @param bookId 前端发来的bookId
     * @return  查询的结果, 一个book实例
     */
    @GetMapping("/find{bookId}")
    public Result find(@PathVariable Integer bookId) {
        try{
        Book book = bookService.getById(bookId);
        return Result.success(book);
        } catch(Exception e){
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 未实现。
     * 分页查询显示图书信息
     * @param page 当前页数
     * @param pageSize 每页的图书信息数
     * @return 对应页的图书信息
     */
    @GetMapping("booksByPage")
    public Result<?> booksListByPage(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "5") Integer pageSize)
    {
        // *排序的条件还没有加入
        List<Book> booksList = bookService.list();
        int pages = (int) booksList.size()/ pageSize;
        // 返回第pageNo页的数据，为Result
        // 假如为第2页，每页10个数据，就是第11到第20条数据
        // pageSize* (pageNo- 1), pageNo*pageSize, [10, 20)
        List<Book> newList = booksList.subList(pageSize* (page- 1), page*pageSize);
        return Result.success(newList);
    }

}
