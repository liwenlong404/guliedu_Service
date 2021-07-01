package com.atguigu.msm.service;

import java.util.Map;

/**
 * @author li
 * @create 2021-05-28 15:27
 */
public interface MsmService {
    boolean send(Map<String, Object> param, String phone);
}
