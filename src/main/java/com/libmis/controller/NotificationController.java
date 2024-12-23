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
    @GetMapping("/categoryList")
    public Result<?> categoryList() {
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

    @DeleteMapping("/del/{categoryId}")
    public Result<?> del(@PathVariable int categoryId) {
        try{
            notificationService.removeById(categoryId);
            return Result.success("删除通知成功了喵。");
        }catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    @GetMapping("/find/{categoryId}")
    public Result<?> find(@PathVariable Integer categoryId) {
        try{
            System.out.println("checkPoint1");
            return Result.success(notificationService.getById(categoryId));
        }catch (Exception e) {
            System.out.println("checkPoint2");
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }
}

