package com.atguigu.edu.serviceedu.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author li
 * @create 2021-05-04 18:11
 */
@Component
@FeignClient(name ="service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    //定义调用的方法的路径
    //根据视频id，删除阿里云的视频
    @DeleteMapping("/eduvod/video/deleteAliVideo/{id}")
    public R deleteAliVideo(@PathVariable("id") String id);

    //删除多个阿里云视频
    //参数多个视频id
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);

}
