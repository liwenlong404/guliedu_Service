package com.atguigu.order.client;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author li
 * @create 2021-06-28 9:54
 */
@Component
@FeignClient("service-edu")
public interface EduClient {
    //根据课程id，查询课程信息
    @PostMapping("/eduservice/courseFront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);
}
