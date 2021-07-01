package com.atguigu.edu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.edu.serviceedu.entity.EduCourse;
import com.atguigu.edu.serviceedu.entity.vo.CourseInfoVo;
import com.atguigu.edu.serviceedu.entity.vo.CoursePublishVo;
import com.atguigu.edu.serviceedu.entity.vo.QueryCourse;
import com.atguigu.edu.serviceedu.service.EduCourseService;
import com.atguigu.edu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author li
 * @since 2021-04-30
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //课程列表 TODO
    //分页
    @GetMapping("getCourseList/{page}/{limit}")
    public R getCourseList(@PathVariable Long page,
                           @PathVariable Long limit){
        //创建page对象
        Page<EduCourse> coursePage = new Page<>(page, limit);

        //调用方法，分页查询
        eduCourseService.page(coursePage, null);

        //从page对象中获取数据
        long total = coursePage.getTotal();
        List<EduCourse> records = coursePage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

    //多条件查询带分页
    @PostMapping("conditionCourseList/{page}/{limit}")
    public R conditionCourseList(@PathVariable Long page,
                                 @PathVariable Long limit,
                                 @RequestBody(required = false) QueryCourse queryCourse){
        Page<EduCourse> coursePage = new Page<>(page, limit);
        //调用方法，进行查询
        eduCourseService.conditionCourseList(coursePage,queryCourse);

        //从page对象中获取数据,并返回
        long total = coursePage.getTotal();
        List<EduCourse> records = coursePage.getRecords();
        return R.ok().data("total",total).data("records",records);

    }

    //添加课程基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
      String id = eduCourseService.saveCourseInfo(courseInfoVo);
      return R.ok().data("courseId",id);
    }

    //根据课程查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
       CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
       return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){

        eduCourseService.updateCourseInfo(courseInfoVo);

        return R.ok();
    }
    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo publishCourse = eduCourseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",publishCourse);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        eduCourseService.deleteCourse(courseId);
        return R.ok();
    }


}

