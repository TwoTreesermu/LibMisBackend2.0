package com.libmis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.Book;
import com.libmis.entity.ReservationRecord;
import com.libmis.mapper.ReservationRecordMapper;
import com.libmis.service.ReservationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;


@Service
public class ReservationRecordServiceImpl extends ServiceImpl<ReservationRecordMapper, ReservationRecord>
        implements ReservationRecordService {
    @Autowired
    private ReservationRecord reservationRecord;
    @Autowired
    private ReservationRecordMapper reservationRecordMapper;

    @Override
    public void reserve(Book book, int userId){

        reservationRecord.setBookId(book.getBookId());
        reservationRecord.setUserId(userId);
        Date today = new Date();
        reservationRecord.setReservationDate(today);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        // 获取修改后的 Date
        Date newDate = calendar.getTime();
        reservationRecord.setExpectedAvailableDate(newDate);
        reservationRecord.setStatus("已预约");
        reservationRecordMapper.insert(reservationRecord);
    }

}
