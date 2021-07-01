package com.atguigu.edu.serviceedu.service;

import com.atguigu.edu.serviceedu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author li
 * @since 2021-04-30
 */
public interface EduVideoService extends IService<EduVideo> {
    //1.根据课程id，删除小节
    void deleteVideoByCourseId(String courseId);
}
