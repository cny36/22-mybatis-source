package com.cny.mybatissource.controller;

import com.cny.mybatissource.dao.UserMapper;
import com.cny.mybatissource.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : chennengyuan
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserMapper userMapper;

    @GetMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    public void addUser(){
        for (int i = 30; i < 40; i++) {
            userMapper.insertSelective(new UserEntity(i, "name" + i));
        }
    }
}
