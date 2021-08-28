package com.planeter.review.model.entity;

import lombok.Data;
import org.bson.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 词书背诵计划
 * MongoDB
 */
@Data
@org.springframework.data.mongodb.core.mapping.Document(collection="wordGroup")
public class WordGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Long userId;
    private String bookId;
    // 乱序整形列表
    private List<Integer> order;
    // 各个复习单元的分界点
    private List<Integer> breaks;
    // 复习单元id列表
    private List<String> unitIds;
    public WordGroup(Long userId,String bookId){
        this.userId = userId;
        this.bookId = bookId;
        List<Integer> breaks= new ArrayList<>();
        List<String> unitIds= new ArrayList<>();
        breaks.add(0);
        this.breaks = breaks;
        this.unitIds = unitIds;
    }
    //生成乱序
    public void generateOrder(Integer wordCount){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < wordCount; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        this.order = list;
    }
}
