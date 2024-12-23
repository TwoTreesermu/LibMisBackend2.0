package com.libmis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.BorrowRecord;
import com.libmis.mapper.BorrowRecordMapper;
import com.libmis.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BorrowRecordServiceImpl extends ServiceImpl<BorrowRecordMapper, BorrowRecord>
        implements BorrowRecordService {
    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    @Override
    public BorrowRecord checkReturnByUserId(int userId){
        return borrowRecordMapper.checkReturnByUserId(userId);
    }
    @Override
    public List<Integer> getByUserId(int userId){
        return borrowRecordMapper.getByUserId(userId);
    }

}
