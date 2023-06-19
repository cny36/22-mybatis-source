package com.cny.mybatissource;

import com.cny.mybatissource.dao.UserMapper;
import com.cny.mybatissource.entity.UserEntity;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyBatisSourceTests {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private UserMapper userMapper;

    @Test
    void selectTest() {
        UserEntity userEntity = userMapper.selectById(1);
        System.out.println(userEntity);
    }

    @Test
    void insertTest() {
        for (int i = 30; i < 40; i++) {
            userMapper.insertSelective(new UserEntity(i, "name" + i));
        }
    }

    @Test
    void sessionSelectTest() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        UserEntity userEntity = mapper.selectById(1);
        System.out.println(userEntity);
    }

}
