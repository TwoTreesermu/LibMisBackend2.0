package com.libmis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author zhiyu
 * @date 2024-12-18 11:40
 */
@Data
@TableName("comment")  // 实体类对应的表名
public class Comment {
    @TableId(type = IdType.AUTO)  // 主键类型是自增长
    private int commentId;  // 评论ID
    private int userId;  // 用户ID（外键关联user表）
    private int bookId;  // 图书ID（外键关联book表）
    private int rating;  // 评分，通常为1-5分
    private String commentText;  // 评论内容
    private Date commentDate;  // 评论日期
    private int likes;  // 点赞数
    private int dislikes;  // 不喜欢数
}
