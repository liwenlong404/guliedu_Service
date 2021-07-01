package com.atguigu.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author li
 * @create 2021-05-28 21:57
 */
@SpringBootApplication
@ComponentScan("com.atguigu")
@EnableDiscoveryClient
@MapperScan("com.atguigu.ucenter.mapper")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
