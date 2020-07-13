package com.ldc.transactionaldemo.mapper;

import com.ldc.transactionaldemo.model.User;

import java.util.List;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/13 22:33
 */
public interface UserMapper {

    List<User> getUserList();

    int insertUser(User user);

}