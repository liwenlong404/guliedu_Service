package com.atguigu.edu.serviceedu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * @create 2021-05-01 21:48
 */
@Data
public class ChapterVo {
    private String id;
    private String title;

    //表示小节
    private List<VideoVo> children=new ArrayList<>();
}
