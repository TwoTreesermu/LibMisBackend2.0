package com.libmis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author zhiyu
 * @date 2024-12-18 11:34
 */
@Data
@TableName("search_history")  // 实体类对应的表名
public class SearchHistory {
    @TableId(type = IdType.AUTO)  // 主键类型是自增长
    private int searchHistoryId;  // 搜索历史ID
    private int userId;  // 用户ID（外键关联user表）
    private String searchQuery;  // 搜索查询内容
    private Date searchTime;  // 搜索时间
}
