package com.planeter.review.model.entity;

import lombok.Data;
import org.bson.Document;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@org.springframework.data.mongodb.core.mapping.Document(collection="unit")
public class ReviewUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private Long userId;
    private Date created;
    //0->背单词， 1->自定义
    private int type;
    // 8次遗忘点记录
    private String record;
    // 状态：1.进行中(1) 2.已结束(0)
    private Integer state;
    private Document content;

    public ReviewUnit() {
    }

    public ReviewUnit(String name, Long userId, int type, Document content) {
        this.name = name;
        this.userId = userId;
        this.created = new Date();
        this.type = type;
        this.record = "00000000";
        this.state = 1;
        this.content = content;
    }
    public ReviewUnit(Long userId, int type, Document content) {
        this.userId = userId;
        this.created = new Date();
        this.type = type;
        this.record = "00000000";
        this.state = 1;
        this.content = content;
    }
}
