package com.atguigu.edu.serviceedu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author li
 * @create 2021-06-28 20:31
 */
@Component
@FeignClient(name = "service-order",fallback = OrderClientImpl.class)
public interface OrderClient  {
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId);
}
