package com.planeter.review.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 词书基本信息, 存储于MySQL
 */
@Data
@Entity
public class WordBook implements Serializable {
    /**
     * 词书id
     */
    @Id
    private String bookId;
    /**
     * 单词总数
     */
    private String total;
    /**
     * 简介
     */
    private String description;
    /**
     * 封面URL
     */
    private String cover;
}
