package com.libmis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.Book;
import com.libmis.entity.BorrowRecord;
import com.libmis.mapper.BorrowRecordMapper;
import com.libmis.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class BorrowRecordServiceImpl extends ServiceImpl<BorrowRecordMapper, BorrowRecord>
        implements BorrowRecordService {
    @Autowired
    private BorrowRecordMapper borrowRecordMapper;
    @Autowired 
    private BorrowRecord borrowRecord;

    @Override
    public BorrowRecord checkReturnByUserId(int userId){
        return borrowRecordMapper.checkReturnByUserId(userId);
    }
    @Override
    public List<Integer> getByUserId(int userId){
        return borrowRecordMapper.getByUserId(userId);
    }
    @Override
    public void returnBook(int userId, int bookId){
        borrowRecordMapper.returnBook(userId, bookId);
    }
    @Override
    public void updateBorrowRecord(int bookId, int userId){
        borrowRecord.setBookId(bookId);
        borrowRecord.setUserId(userId);
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, 30);
        borrowRecord.setBorrowDate(today);
        borrowRecord.setDueDate(calendar.getTime());
        borrowRecord.setStatus("已借出");
        borrowRecordMapper.insert(borrowRecord);
    }
}
