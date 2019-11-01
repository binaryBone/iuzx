package com.iuzx.edu.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程一级分类包装类
 */
@Data
public class SubjectNestedVo {

    private String id;
    private String title;
    private List<SubjectVo> children = new ArrayList<>();

}