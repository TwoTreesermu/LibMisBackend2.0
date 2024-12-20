package com.libmis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libmis.entity.Book;
import com.libmis.utils.Result;
import com.libmis.service.BookService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
@SpringBootTest
@Configuration
@MapperScan("com.libmis.mapper")
public class MpMethodsTest {
    @Autowired
    BookService bookService;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    Book book;
    /*
    @Test
    // 无效测试
    public void testMpMethods() {
        BookService bookService = new BookServiceImpl();
        List<Book> booksList = bookService.list();
        System.out.println(Result.success(booksList));
    }

    @Test
    // 无效测试
    public void del() {
        // 联表删除的问题
        // id不存在时不报错的问题
        try {
            BookService bookService = new BookServiceImpl();
            log.info("checkPoint1");
        }catch (Exception e){
            log.info("checkPoint2");
            log.error(e.getMessage());
        }
    }

    @Test
    // 分页测试
    void testPageQuery(){
        int pageNo = 1, pageSize = 10;
        // 1.1 分页条件
        Page<Book> page = Page.of(pageNo, pageSize);
//        System.out.println(page);
        // 1.2 排序条件
        page.addOrder(OrderItem.desc("bookId"));
//        log.info("checkPoint1");
        // 2. 分页查询
        Page<Book> p = bookService.page(page);
//        log.info("checkPoint2");
        // 3. 解析
        long total = p.getTotal();
//        log.info("checkPoint3");

        System.out.println("total:"+total);
        int pages = (int) p.getPages();
        System.out.println("pages:"+pages);
        List<Book> books = p.getRecords();
//        for(int i = 0; i <books.size(); i++){
//            System.out.println(i);
//            System.out.println(books.get(i));
//        }
    }

    @Test
    void ywqPageQuery(){
        int pageNo = 2;
        int pageSize = 10;
        List<Book> booksList = bookService.list();
        int pages = (int) booksList.size()/ pageSize;
        // 返回第pageNo页的数据，为Result
        // 假如为第2页，每页10个数据，就是第11到第20条数据
        // pageSize* (pageNo- 1), pageNo*pageSize, [10, 20)
        List<Book> newList = booksList.subList(pageSize* (pageNo- 1), pageNo*pageSize);
        newList.forEach(System.out::println);

    }

    @Test
    void getServiceByType() {
        // 通过type值来获取指定的Bean（假设Bean的名称与type完全一致）
        String type = "Book";
        try {
            Class<?> clazz = Class.forName(type);
            int pageSize = 10;
            int pageNum = 2;
            // 1. 新建query
            QueryWrapper<clazz> queryWrapper = Wrappers.query();

            Page<Book> page = bookService.page(new Page<>(pageNum, pageSize), queryWrapper);
            System.out.println(page);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
*/
    @Test
    void getEntity(){
        String type = "Book";
        try{
            Class<?> clazz = Class.forName("com.libmis.entity."+type);
            System.out.println("Book反射类型:   "+ clazz.getClass().getName());
            System.out.println("Book实例类型:   "+ book.getClass().getName());
//            QueryWrapper<?> queryWrapper = Wrappers.query(clazz);
            QueryWrapper<Book> queryWrapper = Wrappers.query();
            Page<Book> page = bookService.page(new Page<>(2, 6), queryWrapper);
//            System.out.println("page ={}"+ page);
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }

//        // 1. 新建query
//        QueryWrapper<entity> queryWrapper = Wrappers.query();
//        // 2. 条件搜索
//        if (StringUtils.hasText(search)) {
//            queryWrapper.like("title", search);
//        }
//        // 3. 填page
//        Page<Book> page = bookService.page(new Page<>(pageNum, pageSize), queryWrapper);
//        System.out.println("page ={}"+ page);
    }








}
