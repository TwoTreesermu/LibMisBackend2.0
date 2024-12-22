package com.libmis.controller;

import com.libmis.entity.Book;
import com.libmis.entity.BorrowRecord;
import com.libmis.service.BorrowRecordService;
import com.libmis.utils.Jwt;
import com.libmis.entity.OperationLog;
import com.libmis.service.UserService;
import com.libmis.utils.Md5Util;
import com.libmis.utils.PageQuery;
import com.libmis.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import com.libmis.entity.User;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.StringRedisTemplate;
import com.libmis.service.OperationLogService;


@Slf4j
@RestController
@RequestMapping("/users") // 具体url待定
public class UserController {

    @Autowired
    private UserService userService; // 注入 userService
    @Autowired
    private PageQuery pageQuery;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    OperationLog operationLog;
    @Autowired
    BorrowRecordService borrowRecordService;
    // 前端以json格式发送数据，需要加@RequestBody
    // 以表单形式提交，则不需要@RequestBody
    @GetMapping("/testTxt")
    public Result<?> testTxt() {
        return Result.success("testInfo by ywqtttu.");
    }

    /**
     * 查询所有用户信息(没有考虑分页查询)
     *
     * @return 所有用户信息，封装在Result 的 data 中
     */
    @GetMapping("/usersList")
    public Result<?> usersList() {
        try {
            List<User> usersList = userService.list();
            log.info("usersList ={}", usersList);
            return Result.success(usersList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 更新用户信息
     *
     * @param user 前端发送来的 user 实例
     * @return 一个不带数据的 Result
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        try {
            userService.saveOrUpdate(user); // 假如没有就新建一个
            // @RequestHeader(name = "Authorization") String token
//          operationLogService.saveOperationLog(null, "update");
            // 存log的部分暂时被注释了，因为前端还没有发送head。
            return Result.success("更新用户数据成功喵。");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 添加用户信息
     *
     * @param user 前端发送来的 user 实例
     * @return 一个不带数据的 Result
     */
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        try {
            userService.save(user);
            return Result.success("保存用户数据成功喵。");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 删除用户信息
     *
     * @param userId 前端发来的 userId
     * @return 一个不带数据的 Result,
     */
    @DeleteMapping("/del/{userId}")
    public Result del(@PathVariable int userId) {
        // *已解决* 联表删除的问题
        // *已解决* id不存在时不报错的问题
        try {
            if (userService.removeById(userId)) {
                return Result.success("删除用户数据成功喵。");
            } else {
                return Result.error("501", "哈麻皮你输的userId不对。");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 根据userId 查询用户信息
     * @param userId 前端发来的userId
     * @return 查询的结果, 一个user实例
     */
    @GetMapping("/find/{userId}")
    public Result find(@PathVariable Integer userId) {
        try {
            User user = userService.getById(userId);
            return Result.success(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 分页显示用户信息
     * @param pageNum 当前页数
     * @param pageSize 每页的用户记录数
     * @return 对应页的用户信息
     */
    @GetMapping("/usersByPage")
    public Result<?> userListByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "5")Integer pageSize
                                    ) {
        return pageQuery.pageQuery("person", pageNum, pageSize, null, null);
    }

    /**
     *查询、检索, 分页显示用户信息
     * @param pageNum 当前页数
     * @param pageSize 每页的用户记录数
     * @param search  查询关键词
     * @return 对应页的用户信息
     */
    @GetMapping("/BySearchPage")
    public Result<?> usersListByConditionPage(@RequestParam(defaultValue = "person")String type,
                                              @RequestParam(defaultValue = "1")Integer pageNum,
                                              @RequestParam(defaultValue = "5")Integer pageSize,
                                              @RequestParam(defaultValue = "")String search) {
        return pageQuery.pageQuery(type, pageNum, pageSize, search, null);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        // 查询用户
        String userName = user.getUserName();
        User existUser = userService.getByUserName(userName);
//        System.out.println(user);
        if (existUser == null){ // 没有被占用
            // 注册
            System.out.println("checkPoint1");
            System.out.println("checkPoint2");
            userService.register(user);
            System.out.println("checkPoint3");
            return Result.success("注册成功了喵");
        }else{
            // 报错
            return Result.error("500","用户名被占用了喵");
        }

    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        // 首先查询用户
        String userName = user.getUserName();
        String password = user.getUserPwd();
        User existUser = userService.getByUserName(userName);

        if (existUser != null){ // 假如存在用户
            if (userService.login(userName, password)){ // 密码正确
                // Redis和拦截器的部分, 暂注释。
                // 取消注释之后前端发送的请求都要带上@RequestHeader(name = "Authorization") String token

                String token = Jwt.getJwt(userName, password);
                ValueOperations<String, String> operations = redisTemplate.opsForValue();
                operations.set("userName", userName);
                operations.set("token", token, 3, TimeUnit.HOURS);
                return Result.success(token); // 发放令牌

                /*
                现在的令牌是由postman手动输入的，而且是我输入给postman再输入进去的
                加入redis之后，其实也只是补全了token增删改的功能而已
                那么实际操作中，浏览器要如何持有并发送token呢？
                */
                // 加入令牌机制以后，应该要返回的是token令牌（一串长字符串）
//                return Result.success("登录了喵");
            } else{
                return Result.error("500", "密码错了喵");
            }// 登录命令
        }else{
            // 报错
            return Result.error("500", "用户不存在");
        }

    }

    //****************************以下为画大饼***********************************
    /**
     * 查看自己借阅了的书籍
     * 这里用的分页查询，暂不知道要不要接收前端传过来的pageNum, pageSize
     */
    @GetMapping("/borrowRecord")

    public Result<?> borrowRecord(@RequestHeader("Authorization") String token) {
        Map<String, Object> userMap = Jwt.verifyToken(token);
        String userName = (String) userMap.get("userName");
        int userId = userService.getByUserName(userName).getUserId();
        return pageQuery.pageQuery("borrowRecord", 1, 10, "id", userId);
    }

    /**
     * 延期归还
     * 应该是用户点开自己的借阅记录，然后还没有还的书边上有一个这个功能按钮
     * 理论上续借的次数是有限的，但是这里先不纠结。
     * 注意前端是否需要设计一下，已经归还的书籍就不该显示续借的按钮。
     * @param book
     * @return
     */
    @PostMapping("/borrowRecord/extendLoan")
    public Result<?> extendLoan(@RequestBody Book book) {
        // 修改record里的时间就可以了
        int bookId = book.getBookId();
        BorrowRecord bookRecord = borrowRecordService.getById(bookId);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bookRecord.getReturnDate());
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        bookRecord.setReturnDate(calendar.getTime());
        return Result.success("还书时间更新为："+ bookRecord.getReturnDate());
    }

    /**
     * 注销
     */
    @PostMapping("/logout")
    public Result<?> logout(@RequestBody User user) {
        System.out.println("**********"+user.getUserName()+ user);
        int userId = user.getUserId();
        // 假如存在未归还书籍
        BorrowRecord borrowRecord = borrowRecordService.checkReturnByUserId(userId);
//        System.out.println("**********"+borrowRecord);
        if (borrowRecord == null){
            try {
                if (userService.removeById(userId)) {
                    return Result.success("注销成功喵。");
                } else {
                    return Result.error("501", "用户不存在");
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                return Result.error("501", e.getMessage());
            }
        }
        else{
            return Result.error("500", "存在借阅事务未结算，无法注销的喵。");
        }
    }

    /**
     * 修改密码
     */
    @PostMapping("/resetPwd")
    // 假如前端传的只有“换了新密码”之后的user就这么写，假如是带上oldPwd和newPwd俩字符串的那就另说
    public Result<?> resetPwd(@RequestBody User user) {
        user.setUserPwd(Md5Util.getMD5String(user.getUserPwd()));
        userService.updateById(user);
        return Result.success("修改密码成功了喵，请重新登录喵。");
    }

    /**
     * 发送评论...先不写
     */

    /**
     * 点赞评论...先不写
     */

    /**
     * 发送公告...暂不写
     */

    /**
     * 添加公告...暂不写
     */

    /**
     * 删除公告...暂不写
     */




}