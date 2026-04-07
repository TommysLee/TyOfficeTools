package com.ty.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 题目实体类
 *
 * @Author Tommy
 * @Date 2025/12/3
 */
@Getter
@Setter
@Accessors(chain = true)
public class Que implements Serializable {

    @Serial
    private static final long serialVersionUID = 1651621980153825299L;

    private Integer index;

    private String id;

    private String html;
}
