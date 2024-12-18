package com.libmis;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libmis.entity.Book;
import com.libmis.service.impl.BookServiceImpl;
import com.libmis.utils.Result;
import org.junit.jupiter.api.Test;
import com.libmis.service.BookService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@SpringBootTest
@MapperScan("com.libmis.mapper")
public class MpMethodsTest {
    @Autowired
    BookService bookService;
//    @Test
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
        log.info("checkPoint1");
        // 2. 分页查询
        Page<Book> p = bookService.page(page);
        log.info("checkPoint2");
        // 3. 解析
        long total = p.getTotal();
        log.info("checkPoint3");

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













}
