package com.planeter.review;

import com.planeter.review.service.ScoreBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ReviewApplicationTests {
    @Resource
    ScoreBoardService scoreBoardService;
    @Test
    void InsertWordBook() {

    }
    @Test
    void scoreBoard() {
        scoreBoardService.increaseOne(1L);
    }
}
