package com.cny.mybatissource.configuration;

import com.cny.mybatissource.plugin.MyBatisInteceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis 相关配置
 *
 * @author : chennengyuan
 */
@Configuration
public class MyBatisConfiguration {

    @Bean
    public MyBatisInteceptor myBatisInteceptor() {
        return new MyBatisInteceptor();
    }
}
