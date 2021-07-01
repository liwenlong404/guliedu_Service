package com.atguigu.sta.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.sta.client.UcenterClient;
import com.atguigu.sta.entity.StatisticsDaily;
import com.atguigu.sta.mapper.StatisticsDailyMapper;
import com.atguigu.sta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author li
 * @since 2021-06-29
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        //远程调用得到某一天的注册人数
        R r = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) r.getData().get("countRegister");

        System.out.println("注册人数"+countRegister.intValue());

        //添加到统计数据表
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister);//注册人数
        sta.setDateCalculated(day);//统计日期



        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));

        baseMapper.insert(sta);


    }

    //图表显示，返回两部分数据，日期json数组，数量json数组
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件，查询数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.orderByAsc("date_calculated");
        wrapper.select("date_calculated",type);//查询日期  数量 两个字段
        List<StatisticsDaily> dailies = baseMapper.selectList(wrapper);
        
        //返回数据有日期和数量
        //因为前段要求数组json，所有后端使用list进行封装
        List<String> dateCalculated = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        for (int i = 0; i < dailies.size(); i++) {
            StatisticsDaily daily = dailies.get(i);
            //封装日期集合
            dateCalculated.add(daily.getDateCalculated());
            //封装数量
            switch (type) {
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("dateCalculatedList",dateCalculated);
        map.put("numDataList",numDataList);

        return map;
    }
}







