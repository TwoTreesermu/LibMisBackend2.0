package com.libmis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libmis.entity.OperationLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


/**
 * @author Ywqtttu
 * @date 2024-12-18
 */
// 每次操作之后都往操作日志库里存一个对象
public interface OperationLogMapper extends BaseMapper<OperationLog> {


}
