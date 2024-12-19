package com.libmis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libmis.service.UserService;
import com.libmis.utils.PageQuery;
import com.libmis.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.libmis.entity.User;
import java.util.List;

/**
 * @author 二木
 * @date 2024-12-17 22:15
 */
@Slf4j
@RestController
@RequestMapping("/users") // 具体url待定
public class UserController {

    @Autowired
    private UserService userService; // 注入 userService
    @Autowired
    private PageQuery pageQuery;
    
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
     * 更新图书信息
     *
     * @param user 前端发送来的 user 实例
     * @return 一个不带数据的 Result
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        try {
            userService.saveOrUpdate(user); // 假如没有就新建一个
            return Result.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 添加图书信息
     *
     * @param user 前端发送来的 user 实例
     * @return 一个不带数据的 Result
     */
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        try {
            userService.save(user);
            return Result.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 删除一本图书信息
     *
     * @param userId 前端发来的 userId
     * @return 一个不带数据的 Result,
     */
    @DeleteMapping("/del/{userId}")
    public Result del(@PathVariable int userId) {
        // 联表删除的问题
        // *已解决* id不存在时不报错的问题
        try {
            if (userService.removeById(userId)) {
                return Result.success();
            } else {
                return Result.error("501", "哈麻皮你输的userId不对。");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 根据userId 查询图书信息
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
     * 分页显示图书信息
     * @param pageNum 当前页数
     * @param pageSize 每页的图书记录数
     * @return 对应页的图书信息
     */
    @GetMapping("/usersByPage")
    public Result<?> userListByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "5")Integer pageSize
                                    ) {
        return pageQuery.pageQuery("User", pageNum, pageSize, null);
    }


    /**
     *查询、检索, 分页显示图书信息
     * @param pageNum 当前页数
     * @param pageSize 每页的图书记录数
     * @param search  查询关键词
     * @return 对应页的图书信息
     */
    @GetMapping("/BySearchPage")
    public Result<?> usersListByConditionPage(@RequestParam(defaultValue = "User")String type,
                                              @RequestParam(defaultValue = "1")Integer pageNum,
                                              @RequestParam(defaultValue = "5")Integer pageSize,
                                              @RequestParam(defaultValue = "")String search) {
        return pageQuery.pageQuery(type, pageNum, pageSize, search);
    }

}