package com.planeter.review.model.param;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户自主上传的复习单元
 */
@Data
public class CustomUnit {
    /**
     * 复习单元名字
     */
    private String name;
    /**
     * 复习单元内容
     */
    private String content;
}
