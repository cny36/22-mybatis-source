package com.cny.mybatissource.dao;

import com.cny.mybatissource.annotation.NeedInterceptor;
import com.cny.mybatissource.entity.UserEntity;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);


    @NeedInterceptor(originalTableName = "t_user", newTableName = "t_user_cmp")
    UserEntity selectById(Integer id);

    UserEntity selectById222(Integer id);

    int insertUser(UserEntity entity);
}