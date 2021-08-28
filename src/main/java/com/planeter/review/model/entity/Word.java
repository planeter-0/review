package com.planeter.review.model.entity;

import lombok.Data;
import org.bson.Document;

import java.io.Serializable;


/**
 * 单词
 * MongoDB
 */
@Data
@org.springframework.data.mongodb.core.mapping.Document(collection="word")
public class Word implements Serializable {
    private int wordRank;

    private String headWord;

    private Document content;

    private String bookId;

}
