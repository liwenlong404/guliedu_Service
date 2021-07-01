package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.config.EduException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantVodUtils;
import com.atguigu.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author li
 * @create 2021-05-04 14:52
 */
@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("uploadAliVideo")
    public R uploadVideo(MultipartFile file){

        String videoId = vodService.uploadVideoToAli(file);

        return R.ok().data("videoId",videoId);
    }

    //根据视频id，删除阿里云的视频
    @DeleteMapping("deleteAliVideo/{id}")
    public R deleteAliVideo(@PathVariable String id){

        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEYID, ConstantVodUtils.KEYSECRET);

            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //向request里设置视频id
            request.setVideoIds(id);

            //调用初始化对象的方法，实现1删除
            client.getAcsResponse(request);

            return R.ok();

        } catch (ClientException e) {
            e.printStackTrace();
            throw new EduException(20001,"删除视频失败");
        }


    }
    //删除多个阿里云视频
    //参数多个视频id
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List videoIdList){

        vodService.deleteMoreAliVideo(videoIdList);

        return R.ok();
    }

    //根据视频id，获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){

        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEYID, ConstantVodUtils.KEYSECRET);

            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();

            //向request里设置视频id
            request.setVideoId(id);

            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            throw new EduException(20001,"获取凭证失败");
        }
    }



}





















