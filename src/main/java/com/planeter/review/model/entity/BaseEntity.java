package com.planeter.review.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

/**
 * 基础实体类
 */
@Data
public abstract class BaseEntity {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public BaseEntity(){
        createdTime = new Date();
        updateTime = new Date();
    }

    public void update(){
        updateTime = new Date();
    }
}
