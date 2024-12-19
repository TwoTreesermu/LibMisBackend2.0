package com.libmis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.User;
import com.libmis.mapper.UserMapper;
import com.libmis.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author 二木
 * @date 2024-12-17 22:07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


}