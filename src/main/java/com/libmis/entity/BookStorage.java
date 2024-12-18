package com.libmis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhiyu
 * @date 2024-12-18 11:18
 */
@Data
@TableName("book_storage") // 实体类对应的表名
public class BookStorage {
    @TableId(type = IdType.AUTO) // 主键类型是自增长
    private int bookId;          // 图书ID
    private int quantity;        // 库存数量
    private int realQuantity;    // 实际库存数量
}
