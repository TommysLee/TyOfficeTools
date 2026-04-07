package com.ty.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 景点
 *
 * @Author Tommy
 * @Date 2025/11/12
 */
@Data
@Accessors(chain = true)
public class Place implements Serializable {

    @Serial
    private static final long serialVersionUID = -7429462806595160254L;

    private String name;

    private String level;
}
