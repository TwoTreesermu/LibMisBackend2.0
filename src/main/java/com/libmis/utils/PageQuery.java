package com.libmis.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libmis.entity.Book;
import com.libmis.entity.User;
import com.libmis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.libmis.service.BookService;


@Component
public class PageQuery {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    public Result<?> pageQuery(String type, int pageNum, int pageSize, String search) {
        if(type.equals("Book")) {
            // 1. 新建query
            QueryWrapper<Book> queryWrapper = Wrappers.query();
            // 2. 条件搜索
            if (StringUtils.hasText(search)) {
                queryWrapper.like("title", search);
            }
            // 3. 填page
            Page<Book> page = bookService.page(new Page<>(pageNum, pageSize), queryWrapper);
            System.out.println("page ={}"+ page);
            return Result.success(page);

        } else if (type.equals("User")) {
            QueryWrapper<User> queryWrapper = Wrappers.query();
            // 2. 条件搜索
            if (StringUtils.hasText(search)) {
                queryWrapper.like("title", search);
            }
            // 3. 填page
//            System.out.println("checkPoint1");
            Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
//            System.out.println("page ={}"+ page);
            return Result.success(page);

        }else{
            return Result.error("500", "实体类型错误");
        }

    }
}
