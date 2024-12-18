package com.libmis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhiyu
 * @date 2024-12-18 11:16
 */
@Data
@TableName("book_category") // 实体类对应的表名
public class BookCategory {
    @TableId(type = IdType.AUTO) // 主键类型是自增长
    private int categoryId;  // 分类ID
    private String categoryName;  // 分类名称
}
