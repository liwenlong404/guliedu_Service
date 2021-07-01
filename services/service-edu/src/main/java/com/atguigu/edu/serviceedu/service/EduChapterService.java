package com.atguigu.edu.serviceedu.service;

import com.atguigu.edu.serviceedu.entity.EduChapter;
import com.atguigu.edu.serviceedu.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author li
 * @since 2021-04-30
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void deleteChapterByCourseId(String courseId);
}
