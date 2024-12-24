package com.libmis.controller;

import com.libmis.entity.Book;
import com.libmis.service.BookService;
import com.libmis.service.BookStorageService;
import com.libmis.service.BorrowRecordService;
import com.libmis.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/books")
public class BorrowRecordController {

    @Autowired
    BookService bookService;
    @Autowired
    BorrowRecordService borrowRecordService;
    @Autowired
    private BookStorageService bookStorageService;

    /**
     * 借书
     * @param bookId
     * @return
     */
    @PostMapping("/borrowBook")
    public Result<?> borrowBook(@Param("bookId") int bookId) {
        /* 先要根据bookId查询Storage库里这个书还有没有库存
           没有库存就报错
           有库存
           1. storage库里这本书的数量减1
           2. 然后返回信息
           所以需要bookStorageMapper链接这个表
         */
        try{
//            Map<String, Object> userMap = Jwt.verifyToken(token);
//            String userName = (String) userMap.get("userName");
//            int userId = userService.getByUserName(userName).getUserId();
            int userId = 1;
            // 同一本只能被借一次，要check一下
            if(bookService.checkBookStorage(bookId, userId)){
                borrowRecordService.updateBorrowRecord(bookId, userId);
                return Result.success("借阅成功了喵。");
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
            bookStorageService.returnBook(book.getBookId());
//            Map<String, Object> userMap = Jwt.verifyToken(token);
//            String userName = (String) userMap.get("username");
//            int userId = userService.getByUserName(userName).getUserId();
            int userId = 1;
            borrowRecordService.returnBook(userId, book.getBookId());
            return Result.success("还书成功了喵。");
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

}
