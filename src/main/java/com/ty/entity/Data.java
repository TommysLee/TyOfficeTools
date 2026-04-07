package com.ty.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 数据实体类
 *
 * @Author Tommy
 * @Date 2025/11/13
 */
@Getter
@Setter
public class Data implements Serializable {

    @Serial
    private static final long serialVersionUID = -9010557671670367147L;

    private String title;

    private String link;

    private String linkText;

    private String pic;

    private List<String> cols;

    private List<String> rows;

    private List<String> list;
}
