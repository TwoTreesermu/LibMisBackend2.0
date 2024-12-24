package com.libmis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.OperationLog;
import com.libmis.entity.User;
import com.libmis.mapper.OperationLogMapper;
import com.libmis.service.OperationLogService;
import com.libmis.utils.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.libmis.service.UserService;
import java.util.Map;
import java.util.Date;
import static java.time.LocalDateTime.now;


@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog>
        implements OperationLogService {
    @Autowired
    private UserService userService;
    @Autowired
    private OperationLog operationLog;
    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public void saveOperationLog(String token, String operationType){
        String userName= Jwt.verifyToken(token);
        User user = userService.getByUserName(userName);
        operationLog.setUserId(user.getUserId());
        operationLog.setOperation(operationType);
        Date date = new Date();
        operationLog.setTimestamp(date);
        operationLogMapper.insert(operationLog);
    }

}
