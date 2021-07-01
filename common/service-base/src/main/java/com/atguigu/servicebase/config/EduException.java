package com.atguigu.servicebase.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author li
 * @create 2021-04-23 16:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EduException extends RuntimeException {
    private  Integer code;//状态码
    private String message;//异常信息



}
