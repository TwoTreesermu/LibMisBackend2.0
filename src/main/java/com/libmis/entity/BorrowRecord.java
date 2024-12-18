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
@TableName("borrow_record")  // 实体类对应的表名
public class BorrowRecord {
    @TableId(type = IdType.AUTO)  // 主键类型是自增长
    private int recordId;  // 借阅记录ID
    private int userId;  // 用户ID（外键关联user表）
    private int bookId;  // 图书ID（外键关联book表）
    private Date borrowDate;  // 借阅日期
    private Date dueDate;  // 应还日期
    private Date returnDate;  // 实际还书日期
    private String status;  // 借阅状态（例如：借阅中、已归还）
    private Double fineAmount;  // 罚款金额
}
