package com.atguigu.edu.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.edu.serviceedu.entity.EduTeacher;
import com.atguigu.edu.serviceedu.entity.vo.QueryTeacher;
import com.atguigu.edu.serviceedu.mapper.EduTeacherMapper;
import com.atguigu.edu.serviceedu.service.EduTeacherService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author li
 * @since 2021-04-22
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {


    //条件查询带分页
    @Override
    public void pageListCondition(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher) {
        //1.判断是否有条件值，有，则拼接条件
        if (queryTeacher == null) {
            //直接查询分页，不进行条件操作
            baseMapper.selectPage(pageTeacher, null);
            return;
        }

        //如果queryTeacher不为空，则取值
        String name=queryTeacher.getName();
        String level=queryTeacher.getLevel();
        String begin=queryTeacher.getBeginTime();
        String end=queryTeacher.getEndTime();

        //判断是否有条件值，有则拼接
        QueryWrapper<EduTeacher> QueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)){
            //拼接
            QueryWrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            QueryWrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            QueryWrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            QueryWrapper.le("gmt_create",end);
        }

        //条件查询带分页
        baseMapper.selectPage(pageTeacher,QueryWrapper);

    }

    //根据id删除
    @Override
    public boolean deleteTeacherById(String id) {
        int result = baseMapper.deleteById(id);
        return result>0 ;
    }

    //分页查询讲师
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageTeacher, wrapper);

        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();


        //把分页数据取出，放入map并返回
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);


        return map;
    }
}
