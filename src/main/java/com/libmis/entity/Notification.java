package com.libmis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author zhiyu
 * @date 2024-12-22 19:56
 */
@Data
@TableName("notification")  // 实体类对应的表名
public class Notification {
    @TableId(type = IdType.AUTO)  // 主键类型是自增长
    private int notificationId;  // 通知ID
    private String title;  // 通知标题
    private String content;  // 通知内容
    private int adminId;  // 发布者ID（外键关联user表）
    private Date publishDate;  // 发布时间
    private Date expireDate;  // 过期时间（可为空）
    private int status;  // 状态（1表示已发布，0表示已撤回）
}
