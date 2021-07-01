package com.atguigu.edu.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.edu.serviceedu.entity.EduSubject;
import com.atguigu.edu.serviceedu.entity.excel.SubjectData;
import com.atguigu.edu.serviceedu.service.EduSubjectService;
import com.atguigu.servicebase.config.EduException;
import com.atguigu.servicebase.config.GlobalExceptionHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.generator.config.IFileCreate;

/**
 * @author li
 * @create 2021-04-29 14:44
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取excel内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData==null){
          throw new EduException(20001,"文件数据为空");
        }

        //一行一行读取，每次读取两个值，第一个值：一级分类，第二个值：二级分类

        //判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject==null){//没有一级分类，进行添加
            existOneSubject=new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }

        //一级分类id值
        String pid=existOneSubject.getId();

        //判断二级分类，并添加
       EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
       if (existTwoSubject==null){
           existTwoSubject=new EduSubject();
           existTwoSubject.setParentId(pid );
           existTwoSubject.setTitle(subjectData.getTwoSubjectName());
           subjectService.save(existTwoSubject);
       }



    }

    //判断一级分类是否重复
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }

    //判断二级分类是否重复
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject two = subjectService.getOne(wrapper);
        return two;
    }



    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
