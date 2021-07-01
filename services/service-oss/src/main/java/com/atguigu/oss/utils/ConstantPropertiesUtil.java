package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 服务器启动，读取配置文件
 * @author li
 * @create 2021-04-26 11:52
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {
    //服务器启动。该类被初始化，然后调用该方法读取配置文件内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.keyId}")
    private String keyId;
    @Value("${aliyun.oss.file.keySecret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucketName}")
    private String bucketName;

    //定义常量，以供使用
    public static String ENDPOINT;
    public static String KEYID;
    public static String KEYSECRET;
    public static String BUCKETNAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT=endpoint;
        KEYID=keyId;
        KEYSECRET=keySecret;
        BUCKETNAME=bucketName;
    }
}
