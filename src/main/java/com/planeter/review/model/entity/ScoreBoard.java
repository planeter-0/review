package com.planeter.review.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 计分板,记录用户超时次数,每月清0
 * MySQL
 */
@Data
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class ScoreBoard implements Serializable {
    @Id
    private Long uid;
    private Date date;
    private Integer times;

    public ScoreBoard(Long uid) {
        this.uid = uid;
        this.date = new Date();
        this.times = 0;
    }

    public ScoreBoard() {

    }
}
