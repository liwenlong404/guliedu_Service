package com.atguigu.edu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.edu.serviceedu.entity.EduChapter;
import com.atguigu.edu.serviceedu.entity.chapter.ChapterVo;
import com.atguigu.edu.serviceedu.entity.vo.CourseInfoVo;
import com.atguigu.edu.serviceedu.service.EduChapterService;
import com.atguigu.edu.serviceedu.service.EduCourseService;
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
//@CrossOrigin
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    //课程大纲列表,根据课程id查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterList(@PathVariable String courseId){
      List<ChapterVo> list=chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }

    //根据章节id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    //删除
    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean result = chapterService.deleteChapter(chapterId);
        if (result){
            return R.ok();
        }else {
            return R.error();
        }

    }

}
















