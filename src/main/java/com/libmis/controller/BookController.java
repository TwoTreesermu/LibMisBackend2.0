package com.libmis.controller;

import com.libmis.entity.Book;
import com.libmis.entity.BookStorage;
import com.libmis.entity.Comment;
import com.libmis.entity.ReservationRecord;
import com.libmis.service.BookService;
import com.libmis.service.BookStorageService;
import com.libmis.service.CommentService;
import com.libmis.service.ReservationRecordService;
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
    @Autowired
    private PageQuery pageQuery;
    @Autowired
    private BookStorageService bookStorageService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReservationRecordService reservationRecordService;
    @Autowired
    private ReservationRecord reservationRecord;

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
            return pageQuery.pageQuery(type, pageNum, pageSize, search, null);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 借书
     * @param book
     * @return
     */
    @PostMapping("/borrowBook")
    public Result<?> borrowBook(@RequestBody Book book) {
        /* 先要根据bookId查询Storage库里这个书还有没有库存
           没有库存就报错
           有库存
           1. storage库里这本书的数量减1
           2. 然后返回信息
           所以需要bookStorageMapper链接这个表
         */
        try{
        if(bookService.checkBookStorage(book)){
            return Result.success("借阅成功");
        }
        else{
            return Result.error("500", "已经没有库存可以出借了喵");}
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 归还图书
     */
    @PostMapping("/returnBook")
    public Result<?> returnBook(@RequestBody Book book) {
        try {
            BookStorage bs = bookStorageService.getById(book.getBookId());
            bs.setRealQuantity(bs.getRealQuantity() + 1);
            bookStorageService.saveOrUpdate(bs);
            return Result.success("还书成功了喵。");
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 增加评论
     */
    @PostMapping("/sendComment")
    public Result<?> sendComment(@RequestBody Comment comment) {
        try {
            comment.setCommentDate(new Date());
            comment.setUserId(1); // 之后从令牌获取
            commentService.saveOrUpdate(comment);
            return Result.success("评论发送成功了喵");
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/delComment")
    public Result<?> del(@RequestBody Comment comment) {
        try {
            commentService.removeById(comment.getCommentId());
            return Result.success("评论删除成功了喵。");
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 查看评论，这里是分页查询
     * 理论上前端也要提供分页参数
     * @return
     */
    @GetMapping("/getCommentByPage")
    public Result<?> getCommentByPage() {
        try {
            return pageQuery.pageQuery("comment", 1, 100, null, null);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 非分页查询的查看评论
     * @return
     */
    @GetMapping("getComment")
    public Result<?> getComment(){
        try {
            return Result.success(commentService.list());
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 给评论点赞
     * @param comment
     * @return
     */
    @PostMapping("/likeComment")
    public Result<?> likeComment(@RequestBody Comment comment) {
        try {
            comment.setLikes(comment.getLikes() + 1);
            commentService.saveOrUpdate(comment);
            return Result.success("点赞成功了喵。");
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 预约书籍
     * @param book
     * @return
     */
    @PostMapping("/reserve")
    public Result<?> reserve(@RequestBody Book book) {
        // userId按理应该是来自token的，但是这里先注释掉了，因为前端还没带token
//        Map<String, Object> userMap = Jwt.verifyToken(token);
//        String userName = (String) userMap.get("userName");
//        int userId = userService.getByUserName(userName).getUserId();
        int userId = 1;
        Date today = new Date();
        reservationRecord.setBookId(book.getBookId());
        reservationRecord.setUserId(userId);
        reservationRecord.setReservationDate(today);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        // 获取修改后的 Date
        Date newDate = calendar.getTime();
        reservationRecord.setExpectedAvailableDate(newDate);
        reservationRecordService.save(reservationRecord);
        return Result.success("预约成功。");
    }

    /**
     * 查看预约记录
     * @return
     */
    @GetMapping("/listReserve")
    public Result<?> listReserve() {
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
     * 删除预约记录
     * @param reservationId
     * @return
     */
    @DeleteMapping("/delReservation")
    public Result<?> delReservation(@PathVariable int reservationId) {
        // 联表删除的问题
        // *已解决* id不存在时不报错的问题
        try {
            if (bookService.removeById(reservationId)) {
                return Result.success("删除成功了喵。");
            } else {
                return Result.error("501", "哈麻皮你输的reservationId不对。");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }
}