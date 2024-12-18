package com.libmis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libmis.entity.Book;
import org.apache.ibatis.annotations.Mapper;



/**
 * @author 二木
 * @date 2024-12-17 22:05
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {

}
