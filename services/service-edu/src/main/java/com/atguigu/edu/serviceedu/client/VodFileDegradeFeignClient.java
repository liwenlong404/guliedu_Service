package com.atguigu.edu.serviceedu.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author li
 * @create 2021-05-06 16:56
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public R deleteAliVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}
