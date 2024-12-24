package com.libmis.controller;


import com.libmis.entity.Book;
import com.libmis.entity.ReservationRecord;
import com.libmis.service.BookService;
import com.libmis.service.ReservationRecordService;
import com.libmis.service.UserService;
import com.libmis.utils.Jwt;
import com.libmis.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.libmis.utils.PageQuery;
@Slf4j
@RestController
@RequestMapping("/books")
public class ReservationRecordController {

   @Autowired
   private ReservationRecordService reservationRecordService;
   @Autowired
   private BookService bookService;
   @Autowired
   private UserService userService;
   @Autowired
   private PageQuery pageQuery;


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
        reservationRecordService.reserve(book, userId);
        return Result.success("预约成功。");
    }

    /**
     * 查看预约记录
     * @return
     */
    @GetMapping("/listReserve")
    public Result<?> listReserve() {
        try {
            List<ReservationRecord> reservationsList = reservationRecordService.list();
            log.info("booksList ={}", reservationsList);
            return Result.success(reservationsList);
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
        try {
            if (reservationRecordService.removeById(reservationId)) {
                return Result.success("删除成功了喵。");
            } else {
                return Result.error("501", "哈麻皮你输的reservationId不对。");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 查询用户的预约记录
     * @param token
     * @return
     */
    @GetMapping("/getReservationRecord")
    public Result<?> getReservationRecord(@RequestHeader("Authorization") String token) {
        try {
            String userName = Jwt.verifyToken(token);
            int userId = userService.getByUserName(userName).getUserId();
            return pageQuery.pageQuery("reservationRecord", 1, 10, "id", userId);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

}
