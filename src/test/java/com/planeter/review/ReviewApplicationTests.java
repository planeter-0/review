package com.planeter.review;

//import com.planeter.review.common.component.BloomFilter;
import com.planeter.review.model.entity.*;
import com.planeter.review.repository.ScoreBoardRepository;
import com.planeter.review.repository.UserRepository;
import com.planeter.review.repository.WordBookRepository;
import com.planeter.review.service.ScoreBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class ReviewApplicationTests {
    //    @Resource
//    ScoreBoardService scoreBoardService;
//    @Test
//    void InsertWordBook() {
//
//    }
//    @Test
//    void scoreBoard() {
//        scoreBoardService.increaseOne(1L);
//    }
//    @Resource
//    MongoTemplate mongoTemplate;
//    @Resource
//    UserRepository userRepository;
//    @Resource
//    BloomFilter bloomFilter;
//
//    @Test
//    void initializeBloomFilter() {
//        for (UserEntity u : userRepository.findAll()) {
//            bloomFilter.getBloomFilter("UserEntity", 100000, 0.003).add(u.getId().toString());
//        }
//        for (ReviewUnit r : mongoTemplate.findAll(ReviewUnit.class)) {
//            bloomFilter.getBloomFilter("ReviewUnit", 100000, 0.003).add(r.getId());
//        }
//        for (WordGroup w : mongoTemplate.findAll(WordGroup.class)) {
//            bloomFilter.getBloomFilter("WordGroup", 100000, 0.003).add(w.getId());
//        }
//    }
}
