package com.atguigu.edu.serviceedu.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.edu.serviceedu.entity.EduCourse;
import com.atguigu.edu.serviceedu.entity.EduTeacher;
import com.atguigu.edu.serviceedu.service.EduCourseService;
import com.atguigu.edu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Wrapper;
import java.util.List;

/**
 * @author li
 * @create 2021-05-27 12:25
 */
@RestController
@RequestMapping("eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前8条热门课程，查询前4条讲师
    @GetMapping("index")
    public R index(){
        //查询前8热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(wrapper);

        //查询前4名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);



        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }

}
