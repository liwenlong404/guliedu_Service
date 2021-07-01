package com.atguigu.sta.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author li
 * @create 2021-06-29 9:37
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {


    //查询某一天的注册人数
    @GetMapping("/educenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
