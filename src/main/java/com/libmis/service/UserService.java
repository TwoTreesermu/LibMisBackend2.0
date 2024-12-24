package com.libmis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libmis.entity.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService extends IService<User> {
    // 如果有其他的需求，可以在该接口声明方法，然后在对应的实现类进行实现
    User getByUserName(String username);
    boolean login(String username, String password);
    void register(User user);
    boolean verifyRole(String token);
}
