package com.atguigu.sta.mapper;

import com.atguigu.sta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 * 网站统计日数据 Mapper 接口
 * </p>
 *
 * @author li
 * @since 2021-06-29
 */
@Mapper
public interface StatisticsDailyMapper extends BaseMapper<StatisticsDaily> {

}
