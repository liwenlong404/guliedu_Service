package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author li
 * @create 2021-04-27 12:29
 */
public interface FileService {
    /**
     * 文件上传至阿里云
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
