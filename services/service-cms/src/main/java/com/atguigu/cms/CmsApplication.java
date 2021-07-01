package com.atguigu.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author li
 * @create 2021-05-27 11:37
 */
@SpringBootApplication
@MapperScan("com.atguigu.cms.mapper")
@EnableDiscoveryClient  //nacos注册
@EnableSwagger2
@EnableCaching
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
