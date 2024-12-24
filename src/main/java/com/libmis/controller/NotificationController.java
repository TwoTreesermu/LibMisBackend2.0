package com.libmis.controller;

import com.libmis.entity.Notification;
import com.libmis.service.NotificationService;
import com.libmis.utils.PageQuery;
import com.libmis.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private PageQuery pageQuery;
    
    // 查询通知表
    @GetMapping("/notificationList")
    public Result<?> notificationList() {
        try {
            List<Notification> categoriesList = notificationService.list();
            return Result.success(categoriesList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }

    }

    // 更新category
    @PutMapping("/update")
    public Result<?> update(@RequestBody Notification notification) {
        try{
            notificationService.saveOrUpdate(notification);
            return Result.success("更新通知成功了喵。");
        }catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Notification notification) {
        try{
            notificationService.save(notification);
            return Result.success("保存通知成功了喵。");
        }catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    @DeleteMapping("/del/{notificationId}")
    public Result<?> del(@PathVariable int notificationId) {
        try{
            notificationService.removeById(notificationId);
            return Result.success("删除通知成功了喵。");
        }catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    @GetMapping("/find/{notificationId}")
    public Result<?> find(@PathVariable Integer notificationId) {
        try{
            return Result.success(notificationService.getById(notificationId));
        }catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    @GetMapping("/BySearchPage")
    public Result<?> notificationListByConditionPage(@RequestParam(defaultValue = "notification")String type,
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

