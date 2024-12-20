package com.libmis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author zhiyu
 * @date 2024-12-18 11:38
 */
@Component
@Data
@TableName("operation_log")  // 实体类对应的表名
public class OperationLog {
    @TableId(type = IdType.AUTO)  // 主键类型是自增长
    private int logId;  // 操作日志ID
    private int userId;  // 用户ID（外键关联user表）
    private String operation;  // 操作描述
    private Date timestamp;  // 操作时间戳
}
