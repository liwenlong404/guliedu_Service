package com.atguigu.edu.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.edu.serviceedu.entity.EduSubject;
import com.atguigu.edu.serviceedu.entity.excel.SubjectData;
import com.atguigu.edu.serviceedu.entity.subject.OneSubject;
import com.atguigu.edu.serviceedu.entity.subject.TwoSubject;
import com.atguigu.edu.serviceedu.listener.SubjectExcelListener;
import com.atguigu.edu.serviceedu.mapper.EduSubjectMapper;
import com.atguigu.edu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author li
 * @since 2021-04-29
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();

            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService))
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //课程分类，树形
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询一级列表
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_Id", "0");
        List<EduSubject> oneList = baseMapper.selectList(oneWrapper);

        //查询二级列表
        QueryWrapper<EduSubject> TwoWrapper = new QueryWrapper<>();
        TwoWrapper.ne("parent_Id", "0");
        List<EduSubject> twoList = baseMapper.selectList(TwoWrapper);

        //创建list集合，用于封装数据
        ArrayList<OneSubject> finalSubjectList = new ArrayList<>();


        //封装一级分类
        for (int i = 0; i < oneList.size(); i++) {
            //取出oneList的各个一级分类学科
            EduSubject eduSubject = oneList.get(i);

            //把eduSubject（一级学科）的值取出来，放到OneSubject对象里
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());

            BeanUtils.copyProperties(eduSubject, oneSubject);


            //再讲多个oneSubject放到finalSubjectList里
            finalSubjectList.add(oneSubject);

            //在一级分类里，循环遍历所有二级分类
            //创建list封装所有一级分类里的二级分类
            ArrayList<TwoSubject> twoFinalSubjectList = new ArrayList<>();

            for (int j = 0; j < twoList.size(); j++) {
                EduSubject twoSubject = twoList.get(j);
                if (twoSubject.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject1 = new TwoSubject();
                    BeanUtils.copyProperties(twoSubject, twoSubject1);
                    twoFinalSubjectList.add(twoSubject1);
                }

            }

            //把二级分类放到一级分类里去
            oneSubject.setChildren(twoFinalSubjectList);

        }





        return finalSubjectList;
    }
}
