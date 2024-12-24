package com.libmis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libmis.entity.BookStorage;
import org.apache.ibatis.annotations.Select;


public interface BookStorageMapper extends BaseMapper<BookStorage>{

    @Select("Select * from borrow_record where book_id = #{bookId} and user_id = #{userId} and status = '已借出'")
    BookStorage checkBorrowed(int bookId, int userId);
}
