package com.atguigu.order.service;

import com.atguigu.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author li
 * @since 2021-06-28
 */
public interface OrderService extends IService<Order> {

    //生成订单
    String createOrders(String courseId, String memberIdByJwtToken);
}
