package com.libmis.service.impl;

import ch.qos.logback.core.util.MD5Util;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.User;
import com.libmis.mapper.UserMapper;
import com.libmis.service.UserService;
import com.libmis.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByUserName(String username) {
        return userMapper.getByUserName(username);
    }

    @Override
    public boolean login(String username, String password) {
        if(Md5Util.getMD5String(password).equals(userMapper.getByUserName(username).getUserPwd())) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void register(User user) {
        user.setUserPwd(Md5Util.getMD5String(user.getUserPwd()));
        userMapper.register(user.getUserName(), user.getUserPwd());
    }
}
