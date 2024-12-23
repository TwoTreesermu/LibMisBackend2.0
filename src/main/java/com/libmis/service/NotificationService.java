package com.libmis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libmis.entity.Notification;
import org.springframework.stereotype.Service;


@Service
public interface NotificationService extends IService<Notification> {
    // 如果有其他的需求，可以在该接口声明方法，然后在对应的实现类进行实现

}
