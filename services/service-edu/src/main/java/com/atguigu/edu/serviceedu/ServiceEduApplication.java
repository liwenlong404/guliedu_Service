package com.atguigu.edu.serviceedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author li
 * @create 2021-04-22 16:53
 */
@SpringBootApplication
@EnableDiscoveryClient//nacos注册
@EnableSwagger2
@EnableFeignClients
public class ServiceEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceEduApplication.class,args);
    }
}
