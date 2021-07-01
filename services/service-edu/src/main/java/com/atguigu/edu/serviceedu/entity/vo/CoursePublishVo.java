package com.atguigu.edu.serviceedu.entity.vo;

import lombok.Data;

/**
 * @author li
 * @create 2021-05-03 15:43
 */
@Data
public class CoursePublishVo {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
