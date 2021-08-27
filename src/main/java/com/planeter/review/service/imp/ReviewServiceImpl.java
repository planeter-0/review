package com.planeter.review.service.imp;

import com.planeter.review.model.entity.ReviewUnit;
import com.planeter.review.service.ReviewService;
import com.planeter.review.service.ScoreBoardService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/8/17 9:26
 * @status dev
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public ReviewUnit createUnit(ReviewUnit reviewUnit) {
        return mongoTemplate.insert(reviewUnit,"unit");
    }

    @Override
    public void deleteUnit(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query,ReviewUnit.class,"unit");
    }

    @Override
    public void updateRecordById(String id,String record) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("record", record);//更新状态
        if(record.charAt(7)!='0')
            update.set("state", 0);
        mongoTemplate.upsert(query,update,"unit");
    }

    @Override
    public ReviewUnit getById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query,ReviewUnit.class);
    }

    @Override
    public List<ReviewUnit> getByUserIdAndState(Long userId,Integer state) {
        Query query = new Query();
        if(state==-1) {
            query.addCriteria(Criteria.where("userId").is(userId));
        } else{
            query.addCriteria(Criteria.where("userId").is(userId).and("state").is(state));
        }
        return mongoTemplate.find(query,ReviewUnit.class);
    }
}