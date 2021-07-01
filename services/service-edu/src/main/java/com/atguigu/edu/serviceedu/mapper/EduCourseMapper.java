package com.atguigu.edu.serviceedu.mapper;

import com.atguigu.edu.serviceedu.entity.EduCourse;
import com.atguigu.edu.serviceedu.entity.frontVo.CourseWebVo;
import com.atguigu.edu.serviceedu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author li
 * @since 2021-04-30
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
