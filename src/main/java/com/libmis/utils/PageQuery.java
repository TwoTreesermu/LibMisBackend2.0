package com.libmis.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libmis.entity.*;
import com.libmis.service.*;
import org.apache.ibatis.ognl.ObjectElementsAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class PageQuery {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private BorrowRecordService borrowRecordService;
    @Autowired
    private CommentService commentService;

    public Result<?> pageQuery(String type, int pageNum, int pageSize, String filter, Object keyWord) {
        if(type.equals("book")) {
            // 1. 新建query
            QueryWrapper<Book> queryWrapper = Wrappers.query();
            // 2. 条件搜索
            if (StringUtils.hasText(filter)) {
                queryWrapper.like(filter, keyWord);
            }
            // 3. 填page
            Page<Book> page = bookService.page(new Page<>(pageNum, pageSize), queryWrapper);
            return Result.success(page);

        }
        else if (type.equals("person")) {
            QueryWrapper<User> queryWrapper = Wrappers.query();
            // 2. 条件搜索
            if (StringUtils.hasText(filter)) {
                queryWrapper.like(filter, keyWord);
            }
            // 3. 填page
            Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
            return Result.success(page);

        }
        else if (type.equals("operationLog")) {
            QueryWrapper<OperationLog> queryWrapper = Wrappers.query();
            // 2. 条件搜索
            if (StringUtils.hasText(filter)) {
                queryWrapper.like(filter, keyWord);
            }
            // 3. 填page
            Page<OperationLog> page = operationLogService.page(new Page<>(pageNum, pageSize), queryWrapper);
            return Result.success(page);
        }
        else if (type.equals("borrowRecord")) {
            QueryWrapper<BorrowRecord> queryWrapper = Wrappers.query();
            // 2. 条件搜索
            if (StringUtils.hasText(filter)) {
                try{queryWrapper.like(filter, keyWord);}
                catch (Exception e){}
            }
            // 3. 填page
            Page<BorrowRecord> page = borrowRecordService.page(new Page<>(pageNum, pageSize), queryWrapper);
            return Result.success(page);
        }
        else if (type.equals("comment")) {
            QueryWrapper<Comment> queryWrapper = Wrappers.query();
            // 2. 条件搜索
            if (StringUtils.hasText(filter)) {
                queryWrapper.like(filter, keyWord);
            }
            // 3. 填page
            Page<Comment> page = commentService.page(new Page<>(pageNum, pageSize), queryWrapper);
            return Result.success(page);
        }
        else{
            return Result.error("500", "实体类型错误");
        }
    }
}
