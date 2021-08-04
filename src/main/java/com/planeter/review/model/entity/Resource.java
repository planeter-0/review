package com.planeter.review.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户自主上传的资源
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Resource extends BaseEntity{
    /**
     * 路径
     */
    private String path;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型。0为文本，1为图片
     */
    private Integer type;
}
