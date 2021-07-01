package com.atguigu.edu.serviceedu.service.impl;

import com.atguigu.edu.serviceedu.entity.EduCourse;
import com.atguigu.edu.serviceedu.entity.EduCourseDescription;
import com.atguigu.edu.serviceedu.entity.frontVo.CourseFrontVo;
import com.atguigu.edu.serviceedu.entity.frontVo.CourseWebVo;
import com.atguigu.edu.serviceedu.entity.vo.CourseInfoVo;
import com.atguigu.edu.serviceedu.entity.vo.CoursePublishVo;
import com.atguigu.edu.serviceedu.entity.vo.QueryCourse;
import com.atguigu.edu.serviceedu.mapper.EduCourseMapper;
import com.atguigu.edu.serviceedu.service.EduChapterService;
import com.atguigu.edu.serviceedu.service.EduCourseDescriptionService;
import com.atguigu.edu.serviceedu.service.EduCourseService;
import com.atguigu.edu.serviceedu.service.EduVideoService;
import com.atguigu.servicebase.config.EduException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author li
 * @since 2021-04-30
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;


    //添加课程信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert<=0){
            throw new EduException(20001,"添加课程失败");
        }

        String cid = eduCourse.getId();

        //2.向课程简介表添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //3.设置描述表id
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);

        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1.查询课程表内容
        EduCourse eduCourse = baseMapper.selectById(courseId);

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        //2.查询课程描述表内容
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());


        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1.修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);

        if (update==0){
            throw new EduException(20001,"修改课程信息失败");
        }
        //2.修改课程描述
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());

         courseDescriptionService.updateById(description);

    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    //删除课程
    @Override
    public void deleteCourse(String courseId) {
        //1.根据课程id，删除小节
        eduVideoService.deleteVideoByCourseId(courseId);
        //2.根据课程id，删除章节
        eduChapterService.deleteChapterByCourseId(courseId);
        //3.根据课程id，删除课程描述
        courseDescriptionService.removeById(courseId);
        //4.根据课程id，删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result==0){
            throw new EduException(20001,"删除失败");
        }
    }

    //条件查询带分页
    @Override
    public void conditionCourseList(Page<EduCourse> coursePage, QueryCourse queryCourse) {
        //1.判断是否有条件值，有，则拼接条件
        if (queryCourse==null){
            baseMapper.selectPage(coursePage, null);
            return;
        }
        //如果queryCourse不为空，则取值
        String title=queryCourse.getTitle();
        String status=queryCourse.getStatus();

        //判断是否有条件值，有则拼接
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (title!=null){
            wrapper.like("title", title);
        }
        if (status!=null){
            wrapper.eq("status", status);
        }

        baseMapper.selectPage(coursePage, wrapper);
    }

    @Override
    public Map<String, Object> getCourseFromList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){//判断一级分类是否为空，不为空拼接条件
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());

        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());

        }

        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){//you值则排序
          wrapper.orderByDesc("buy_count");

        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageCourse,wrapper);

        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();
        boolean hasPrevious = pageCourse.hasPrevious();

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

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}











