package com.atguigu.edu.serviceedu.service.impl;

import com.atguigu.edu.serviceedu.entity.EduChapter;
import com.atguigu.edu.serviceedu.entity.EduVideo;
import com.atguigu.edu.serviceedu.entity.chapter.ChapterVo;
import com.atguigu.edu.serviceedu.entity.chapter.VideoVo;
import com.atguigu.edu.serviceedu.mapper.EduChapterMapper;
import com.atguigu.edu.serviceedu.service.EduChapterService;
import com.atguigu.edu.serviceedu.service.EduVideoService;
import com.atguigu.servicebase.config.EduException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author li
 * @since 2021-04-30
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;


    //课程大纲列表,根据课程id查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1.根据课程id查询课程的所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);


        //2.查询课程id查询课程的所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideos = videoService.list(wrapperVideo);

        //创建list集合，用于封装最终数据
        ArrayList<ChapterVo> finalList = new ArrayList<>();

        //3.遍历查询章节的list集合进行封装
        //一一取出chapter放入对应vo，然后将vo放入集合
        for (int i = 0; i < eduChapters.size(); i++) {
            EduChapter eduChapter = eduChapters.get(i);
            //eduChapter
            ChapterVo chapterVo=new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);


            //4.遍历查询小节的list集合进行封装
            ArrayList<VideoVo> videoVos = new ArrayList<>();

            for (int m = 0; m < eduVideos.size(); m++) {
                EduVideo eduVideo = eduVideos.get(m);
                //判断小节的chapter与章节的id是否一致,然后封装
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);

                    //放入小节封装的集合
                    videoVos.add(videoVo);
                }
            }

            //小节的封装list，放入章节对象中去
            chapterVo.setChildren(videoVos);

            //把chapter放入最终的list
            finalList.add(chapterVo);
        }



        return finalList;
    }

    //删除章节
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据章节id查询是否有小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);
        //判断
        if (count>0){
            //能查询小节，不删除
            throw new EduException(20001,"不能删除");
        }else {
            //可以删除
            int result = baseMapper.deleteById(chapterId);
            //成功 1>0
            return result>0;
        }

    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}























