package com.atguigu.edu.serviceedu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * @create 2021-04-30 18:43
 */
@Data
public class OneSubject {
    private String id;
    private String title;

    private List<TwoSubject> children = new ArrayList<>();
}
