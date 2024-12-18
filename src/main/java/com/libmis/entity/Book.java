package com.libmis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 二木
 * @date 2024-12-17 21:55
 */
@Data
@TableName("book") // 实体类对应的表名
public class Book {
    @TableId(type= IdType.AUTO)  // 主键类型是自增长
    private int BookId;  // id
    private String isbn;  // 图书编号
    private int categoryId;  // 图书分类ID
    private String title;  // 书名
    private String author;  // 作者
    private String publisher; // 出版社
    private Date publishDate;  // 出版时间
    private String introduction;  // 简介
    private Double price;  // 价格
    private String coverPic; // 图书封面
    private int borrowedCount; // 此书被借阅次数
}
