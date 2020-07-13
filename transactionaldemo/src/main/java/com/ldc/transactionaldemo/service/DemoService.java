package com.ldc.transactionaldemo.service;

import com.ldc.transactionaldemo.common.TliiServerException;
import com.ldc.transactionaldemo.mapper.UserMapper;
import com.ldc.transactionaldemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/13 22:12
 */
@Service
//@Transactional//类加事务管理
public class DemoService {

    @Autowired
    private UserMapper userMapper;

    //@Transactional//方法加事务管理
    public String save(User user) {
        String result = null;
        int resultCount = userMapper.insertUser(user);
        if (resultCount == 1 ) {
            throw new TliiServerException("测试异常");
        }
        user.setId("8");
        user.setName("李鸿章");
        userMapper.insertUser(user);
        return "success";
    }

    public String create(User user) {//不在事务管理范围内
        String result = null;
        int resultCount = userMapper.insertUser(user);
        if (resultCount == 1 ) {
            throw new TliiServerException("测试异常");
        }
        user.setId("8");
        user.setName("李鸿章");
        userMapper.insertUser(user);
        return "success";
    }

    public Object getList() {
        return userMapper.getUserList();
    }

}
