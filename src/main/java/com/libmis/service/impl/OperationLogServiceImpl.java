package com.libmis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.OperationLog;
import com.libmis.entity.User;
import com.libmis.mapper.OperationLogMapper;
import com.libmis.service.OperationLogService;
import com.libmis.utils.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.libmis.entity.OperationLog;
import com.libmis.service.UserService;
import java.util.Map;

import static java.time.LocalDateTime.now;


@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog>
        implements OperationLogService {
    @Autowired
    private OperationLogMapper operationLogMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private OperationLog operationLog;
    @Qualifier("operationLogService")
    @Autowired
    private OperationLogService operationLogService;

    @Override
    public void saveOperationLog(String token, String operationType){
        Map<String, Object> userMap = Jwt.verifyToken(token);
        User user = userService.getByUserName((String) userMap.get("userName"));
        operationLog.setUserId(user.getUserId());
        operationLog.setOperation(operationType);
        operationLog.setTimestamp(now());
        operationLogService.save(operationLog);
    }

}
