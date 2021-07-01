package com.atguigu.sta.schedule;

import com.atguigu.sta.service.StatisticsDailyService;
import com.atguigu.sta.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author li
 * @create 2021-06-29 11:56
 */
@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService dailyService;

//    @Scheduled(cron = "0/5 * * * * ?")
//    public void task1(){
//        System.out.println("***********Task**********");
//    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }

}
