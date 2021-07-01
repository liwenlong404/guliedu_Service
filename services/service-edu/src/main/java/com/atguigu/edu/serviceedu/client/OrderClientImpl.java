package com.atguigu.edu.serviceedu.client;

import org.springframework.stereotype.Component;

/**
 * @author li
 * @create 2021-06-28 20:32
 */
@Component
public class OrderClientImpl implements OrderClient{
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
