package com.libmis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libmis.entity.Book;
import com.libmis.entity.BorrowRecord;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface BorrowRecordService extends IService<BorrowRecord> {
    // 如果有其他的需求，可以在该接口声明方法，然后在对应的实现类进行实现
    BorrowRecord checkReturnByUserId(int userId);
    List<Integer> getByUserId(int userId);
    void updateBorrowRecord(int bookId,  int userId);
    void returnBook(int userId, int bookId);
}
