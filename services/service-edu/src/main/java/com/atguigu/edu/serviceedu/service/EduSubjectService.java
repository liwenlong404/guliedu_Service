package com.atguigu.edu.serviceedu.service;

import com.atguigu.edu.serviceedu.entity.EduSubject;
import com.atguigu.edu.serviceedu.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author li
 * @since 2021-04-29
 */
public interface EduSubjectService extends IService<EduSubject> {
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
