package com.atguigu.commonutils;

/**
 * 定义返回数据使用的状态吗
 * @author li
 * @create 2021-04-23 14:10
 */
public interface ResultCode {

    Integer SUCCESS = 20000;//成功
    Integer Error = 20001;//失败
    Integer AUTH = 30000;//没有操作权限
}
