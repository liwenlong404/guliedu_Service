package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author li
 * @create 2021-05-04 14:53
 */
public interface VodService {
    String uploadVideoToAli(MultipartFile file);

    void deleteMoreAliVideo(List<String> videoIdList);
}
