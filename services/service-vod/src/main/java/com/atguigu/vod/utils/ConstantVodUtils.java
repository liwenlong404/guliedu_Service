package com.atguigu.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author li
 * @create 2021-05-04 15:03
 */
@Component
public class ConstantVodUtils implements InitializingBean {

    @Value("${aliyun.oss.file.keyId}")
    private String keyId;
    @Value("${aliyun.oss.file.keySecret}")
    private String keySecret;

    public static String KEYID;
    public static String KEYSECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        KEYID=keyId;
        KEYSECRET=keySecret;
    }
}
