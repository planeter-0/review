package com.planeter.review.service.imp;

import com.planeter.review.common.annotation.Cache;
import com.planeter.review.model.entity.Word;
import com.planeter.review.model.entity.WordGroup;
import com.planeter.review.service.WordService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {
    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public WordGroup createGroup(Long userId, String bookId, Integer total) {
        WordGroup group = new WordGroup(userId,bookId);
        group.generateOrder(total);
        return mongoTemplate.insert(group);
    }

    @Override
    @Cache(value = "WordGroup", key = "#groupId")
    public WordGroup getGroupById(String groupId) {
        Query query = new Query(Criteria.where("_id").is(groupId));
        return mongoTemplate.findOne(query,WordGroup.class,"wordGroup");
    }

    @Override
    public List<Word> getWordListBywordRanksAndBookId(List<Integer> wordRanks,String bookId) {
        Query query = new Query(Criteria.where("bookId").is(bookId).and("wordRank").in(wordRanks));
        return mongoTemplate.find(query,Word.class,"word");
    }

    @Override
    @CachePut(value = "WordGroup", key = "#groupId")
    public WordGroup updateGroup(Integer start, String groupId, List<Integer> breaks, List<String> unitIds) {
        Query query = new Query(Criteria.where("_id").is(groupId));
        Update update = new Update();
        update.set("breaks",breaks);
        update.set("unitIds",unitIds);
        FindAndModifyOptions options= new FindAndModifyOptions();
        options.returnNew(true);
        return mongoTemplate.findAndModify(query,update,options,WordGroup.class);
    }
}
