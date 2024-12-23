package com.libmis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libmis.entity.BorrowRecord;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface BorrowRecordMapper extends BaseMapper<BorrowRecord>{

    @Select("Select * from borrow_record where user_id = #{userId} and status = '已借出' ")
    BorrowRecord checkReturnByUserId(int userId);

    @Select("Select book_id from borrow_record where user_id = #{userId} ")
    List<Integer> getByUserId(int userId);
}
