package com.planeter.review.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/8/25 20:28
 * @status dev
 */
@Data
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class ScoreBoard {
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
