package com.atguigu.edu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.edu.serviceedu.entity.subject.OneSubject;
import com.atguigu.edu.serviceedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author li
 * @since 2021-04-29
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("import")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    //课程分类列表
    @GetMapping("getAllSubject")
    public R getAllSubject(){
       List<OneSubject> list = subjectService.getAllOneTwoSubject();
       return R.ok().data("list",list);
    }

}

