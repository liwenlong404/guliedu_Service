package com.atguigu.edu.serviceedu.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.edu.serviceedu.client.OrderClient;
import com.atguigu.edu.serviceedu.entity.EduCourse;
import com.atguigu.edu.serviceedu.entity.EduTeacher;
import com.atguigu.edu.serviceedu.entity.chapter.ChapterVo;
import com.atguigu.edu.serviceedu.entity.frontVo.CourseFrontVo;
import com.atguigu.edu.serviceedu.entity.frontVo.CourseWebVo;
import com.atguigu.edu.serviceedu.service.EduChapterService;
import com.atguigu.edu.serviceedu.service.EduCourseService;
import com.atguigu.edu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author li
 * @create 2021-06-21 15:30
 */
@RestController
@RequestMapping("eduservice/courseFront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    //1.条件查询带分页
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {

        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFromList(pageCourse, courseFrontVo);

        return R.ok().data(map);
    }

    //课程详情
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id，编写sql语句，查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id，查询章节和小节
        List<ChapterVo> courseVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //根据课程id和用户id查询当前课程是否已经购买
        boolean isBuyCourse = orderClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));

        return R.ok().data("courseWebVo",courseWebVo).data("courseVideoList",courseVideoList).data("isBuy",isBuyCourse);

    }

    //根据课程id，查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo, courseWebVoOrder);
        return courseWebVoOrder;
    }



}
