package com.atguigu.edu.serviceedu.controller;

import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atguigu.edu.serviceedu.entity.EduTeacher;
import com.atguigu.edu.serviceedu.entity.vo.QueryTeacher;
import com.atguigu.edu.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author li
 * @since 2021-04-22
 */
@RestController//使返回数据为json
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;


    //模拟登陆
    @PostMapping("login")
    public R login(){
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }



    //根据id修改的方法
    @PostMapping("updateTeacher/{id}")
    public R updateTeacher(@PathVariable String id,
                           @RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据id查询讲师
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }


    //多条件组合查询带分页
    @PostMapping("moreConditionPageList/{page}/{limit}")
    public R moreConditionPageList(@PathVariable Long page,
                                   @PathVariable Long limit,
                                   @RequestBody(required = false) QueryTeacher queryTeacher){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        //调用service的方法实现条件查询并分页
        eduTeacherService.pageListCondition(pageTeacher,queryTeacher);

        //从pageTeacher中获取数据
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return  R.ok().data("total",total).data("items",records);
    }


    //分页查询讲师列表
    @GetMapping("pageList/{page}/{limit}")
    public R getPageTeacherList(@PathVariable Long page,
                                @PathVariable Long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        //调用方法，分页查询
        eduTeacherService.page(pageTeacher,  null);
        //从pageTeacher中获取数据
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return  R.ok().data("total",total).data("items",records);
    }

    //添加讲师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error() ;
        }

    }

    //1.查询所有讲师
    @GetMapping("findAll")
    public R getAllTeacherList(){
        List<EduTeacher> TeacherList = eduTeacherService.list(null);

        return R.ok().data("items",TeacherList);
    }

    //2.逻辑删除讲师
    @DeleteMapping("{id}")
    public R deleteTeacherById(@PathVariable String id){
        boolean result = eduTeacherService.deleteTeacherById(id);
        if (result){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

