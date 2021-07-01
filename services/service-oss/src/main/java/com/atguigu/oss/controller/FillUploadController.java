package com.atguigu.oss.controller;

/**
 * @author li
 * @create 2021-04-27 12:53
 */
import com.atguigu.commonutils.R;
import com.atguigu.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Api(description="阿里云文件管理")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/ossservice/oss")
public class FillUploadController {
    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param file
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    //@ApiParam(name = "file", value = "文件", required = true)
    public R upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file
            ) {

        String uploadUrl = fileService.upload(file);
        //返回r对象
        return R.ok().message("文件上传成功").data("imgUrl", uploadUrl);
    }

    @PostMapping("uploadCourseImg")
    public R uploadCourseImg(@RequestParam("file") MultipartFile file) {
        String uploadUrl = fileService.upload(file);
        //返回r对象
        return R.ok().message("文件上传成功").data("imgUrl", uploadUrl);
    }
}