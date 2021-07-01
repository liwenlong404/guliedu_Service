package com.atguigu.edu.serviceedu.service.impl;

import com.atguigu.edu.serviceedu.client.VodClient;
import com.atguigu.edu.serviceedu.entity.EduVideo;
import com.atguigu.edu.serviceedu.mapper.EduVideoMapper;
import com.atguigu.edu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author li
 * @since 2021-04-30
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    //注入vodClient
    @Autowired
    private VodClient vodClient;

    //1.根据课程id，删除小节
    // TODO 删除视频
     @Override
    public void deleteVideoByCourseId(String courseId) {


         //1，根据课程id，查询课程的所有视频id
         QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
         wrapperVideo.eq("course_id", courseId);
         wrapperVideo.select("video_source_id");
         List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);

         //list<EduVideo>转化为list<String>
         ArrayList<String> videoIds = new ArrayList<>();

         for (int i = 0; i < eduVideoList.size(); i++) {
             EduVideo eduVideo = eduVideoList.get(i);
             String videoSourceId = eduVideo.getVideoSourceId();
             if (!StringUtils.isEmpty(videoSourceId)){
                 videoIds.add(videoSourceId);
             }
         }

         if (videoIds.size()>0){
             //多个视频id，删除视频
             vodClient.deleteBatch(videoIds);
         }


         QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
