package com.atguigu.sta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author li
 * @create 2021-06-29 9:22
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.atguigu.sta.mapper")
@ComponentScan(basePackages = {"com.atguigu"})
@EnableScheduling //开启定时任务
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}
