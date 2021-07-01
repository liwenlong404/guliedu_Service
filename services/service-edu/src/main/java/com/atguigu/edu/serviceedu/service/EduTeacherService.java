package com.atguigu.edu.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.edu.serviceedu.entity.EduTeacher;
import com.atguigu.edu.serviceedu.entity.vo.QueryTeacher;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author li
 * @since 2021-04-22
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //条件查询带分页
    void pageListCondition(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher);

    boolean deleteTeacherById(String id);

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
