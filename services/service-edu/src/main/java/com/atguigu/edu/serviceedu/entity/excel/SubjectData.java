package com.atguigu.edu.serviceedu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 创建实体类和excel对应关系
 * @author li
 * @create 2021-04-29 14:31
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
