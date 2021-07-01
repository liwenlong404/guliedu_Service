package com.atguigu.edu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.edu.serviceedu.client.VodClient;
import com.atguigu.edu.serviceedu.entity.EduVideo;
import com.atguigu.edu.serviceedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author li
 * @since 2021-04-30
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    //注入vodClient
    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){

        //根据小节id，查询视频id
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();

        //判断有没有视频id
        if (!StringUtils.isEmpty(videoSourceId)){
            //根据视频id，远程调用，删除阿里云的视频
            vodClient.deleteAliVideo(videoSourceId);
        }


        videoService.removeById(id);
         return R.ok();
    }

    //更新小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

    //根据id查询小节
    @GetMapping("getVideo/{id}")
    public R getVideo(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        return R.ok().data("video",video);
    }
}

