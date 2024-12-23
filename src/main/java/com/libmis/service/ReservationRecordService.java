package com.libmis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libmis.entity.ReservationRecord;
import org.springframework.stereotype.Service;
import com.libmis.entity.Book;

@Service
public interface ReservationRecordService extends IService<ReservationRecord> {
    // 如果有其他的需求，可以在该接口声明方法，然后在对应的实现类进行实现
    void reserve(Book book, int userId);
}
