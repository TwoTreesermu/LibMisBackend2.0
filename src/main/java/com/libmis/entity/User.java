package com.libmis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author zhiyu
 * @date 2024-12-18 11:30
 */
@Component
@Data
@TableName("user")  // 实体类对应的表名
public class User {
    @TableId(type = IdType.AUTO)  // 主键类型是自增长
    private int userId;  // 用户ID
    private String userName;  // 用户名
    private String phoneNumber;  // 电话号码
    private String emailAdd;  // 电子邮件地址
    private String userPwd;  // 用户密码
    private String portrait;  // 用户头像
    private int role;  // 用户角色：1表示管理员，0表示普通用户
}
