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
@TableName("reservation_record")  // 实体类对应的表名
public class ReservationRecord {
    @TableId(type = IdType.AUTO)  // 主键类型是自增长
    private int reservationId;  // 预约记录ID
    private int userId;  // 用户ID（外键关联user表）
    private int bookId;  // 图书ID（外键关联book表）
    private Date reservationDate;  // 预约日期
    private Date expectedAvailableDate;  // 预计可借日期
    private String status;  // 预约状态（例如：已预约、已取消、已借出）
}
