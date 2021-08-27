package com.planeter.review.model.entity;

import lombok.Data;
import org.bson.Document;

import java.io.Serializable;


/**
 * @author Planeter
 * @description: TODO
 * @date 2021/8/10 22:35
 * @status dev
 */
@Data
@org.springframework.data.mongodb.core.mapping.Document(collection="word")
public class Word implements Serializable {
    private int wordRank;

    private String headWord;

    private Document content;

    private String bookId;

}
