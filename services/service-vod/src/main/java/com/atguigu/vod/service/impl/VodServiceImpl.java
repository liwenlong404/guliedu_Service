package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.config.EduException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantVodUtils;
import com.atguigu.vod.utils.InitVodClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author li
 * @create 2021-05-04 14:53
 */
@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoToAli(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantVodUtils.KEYID,
                    ConstantVodUtils.KEYSECRET,
                    title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                if(StringUtils.isEmpty(videoId)){
                    throw new EduException(20001, errorMessage);
                }
            }

            return videoId;
        } catch (IOException e) {
            throw new EduException(20001, "guli vod 服务上传失败");
        }
    }

    //删除多个阿里云视频
    @Override
    public void deleteMoreAliVideo(List videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEYID, ConstantVodUtils.KEYSECRET);

            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //videoList转换格式
            String videoIds = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");


            //向request里设置视频id
            request.setVideoIds(videoIds);

            //调用初始化对象的方法，实现1删除
            client.getAcsResponse(request);


        } catch (ClientException e) {
            e.printStackTrace();
            throw new EduException(20001,"删除视频失败");
        }


    }
}












