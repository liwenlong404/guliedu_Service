package com.atguigu.edu.serviceedu.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.edu.serviceedu.entity.EduCourse;
import com.atguigu.edu.serviceedu.entity.EduTeacher;
import com.atguigu.edu.serviceedu.service.EduCourseService;
import com.atguigu.edu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;
import java.util.Map;

/**
 * @author li
 * @create 2021-06-21 15:30
 */
@RestController
@RequestMapping("eduservice/teacherFront")
//@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //1.分页查询讲师
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {

        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(pageTeacher);

        //返回分页中的所有数据
        return R.ok().data(map);

    }

    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable long teacherId){
        //1.根据讲师id讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);

        //2.根据讲师id查询所讲课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> eduCourseList = courseService.list(wrapper);

        return R.ok().data("teacher",eduTeacher).data("courseList",eduCourseList);
    }

}
