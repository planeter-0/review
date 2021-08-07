package com.planeter.review.model.entity;

import lombok.Data;

import javax.persistence.Entity;


@Data
@Entity
public class ReviewProject extends BaseEntity{
    private String name;
    private Long userId;
    private String content;
}
