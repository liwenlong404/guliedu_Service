package com.atguigu.edu.serviceedu.service;

import com.atguigu.edu.serviceedu.entity.EduCourse;
import com.atguigu.edu.serviceedu.entity.frontVo.CourseFrontVo;
import com.atguigu.edu.serviceedu.entity.frontVo.CourseWebVo;
import com.atguigu.edu.serviceedu.entity.vo.CourseInfoVo;
import com.atguigu.edu.serviceedu.entity.vo.CoursePublishVo;
import com.atguigu.edu.serviceedu.entity.vo.QueryCourse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author li
 * @since 2021-04-30
 */
public interface EduCourseService extends IService<EduCourse> {



    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void deleteCourse(String courseId);

    void conditionCourseList(Page<EduCourse> coursePage, QueryCourse queryCourse);

    Map<String, Object> getCourseFromList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
