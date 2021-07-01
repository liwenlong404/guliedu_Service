package com.atguigu.edu.serviceedu.entity.vo;

import lombok.Data;

/**
 * 用于封装查询的条件值
 * @author li
 * @create 2021-04-23 15:29
 */
@Data
public class QueryTeacher {

    private String name;
    private String level;
    private String beginTime;
    private String endTime;


}
