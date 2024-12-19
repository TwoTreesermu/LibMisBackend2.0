package com.libmis.controller;

import com.libmis.entity.BookCategory;
import com.libmis.service.BookCategoryService;
import com.libmis.utils.PageQuery;
import com.libmis.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author 二木
 * @date 2024-12-17 22:15
 */
@Slf4j
@RestController
@RequestMapping("/bookCategory")
public class BookCategoryController {

    @Autowired
    private BookCategoryService bookCategoryService;
    @Autowired
    private PageQuery pageQuery;

    // 前端以json格式发送数据，需要加@RequestBody
    // 以表单形式提交，则不需要@RequestBody
    // 查询图书分类表
    @GetMapping("/categoryList")
    public Result<?> categoryList() {
        try {
            List<BookCategory> categoriesList = bookCategoryService.list();
//            log.info("catogoriesList ={}", categoriesList);
            return Result.success(categoriesList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }

    }
    // 更新category
    @PutMapping("/update")
    public Result<?> update(@RequestBody BookCategory bookCategory) {
        try{
            bookCategoryService.saveOrUpdate(bookCategory);
            return Result.success("更新图书分类成功了喵。");
        }catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody BookCategory bookCategory) {
        try{
            bookCategoryService.save(bookCategory);
            return Result.success("保存图书分类成功了喵。");
        }catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    @DeleteMapping("/del/{categoryId}")
    public Result<?> del(@PathVariable int categoryId) {
        try{
            bookCategoryService.removeById(categoryId);
            return Result.success("删除图书分类成功了喵。");
        }catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }
    @GetMapping("/find/{categoryId}")
    public Result<?> find(@PathVariable Integer categoryId) {
        try{
            System.out.println("checkPoint1");
            return Result.success(bookCategoryService.getById(categoryId));
        }catch (Exception e) {
            System.out.println("checkPoint2");
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }
}

