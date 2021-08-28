package com.planeter.review.controller.api;

import com.planeter.review.model.entity.*;
import com.planeter.review.model.param.WordListParam;
import com.planeter.review.service.ReviewService;
import com.planeter.review.service.WordService;
import org.apache.shiro.SecurityUtils;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 单词以及背诵计划
 */
@RestController
public class WordController {
    @Resource
    WordService wordService;
    @Resource
    ReviewService reviewService;

    @PostMapping("/wordGroup/create")
    public WordGroup createGroup(@RequestParam String bookId,
                                 @RequestParam Integer total) {
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        return wordService.createGroup(user.getId(), bookId, total);
    }

    @GetMapping("/wordGroup/{groupId}")
    public WordGroup getGroupById(@PathVariable String groupId) {
        return wordService.getGroupById(groupId);
    }

    @PutMapping("/wordGroup/newUnit")
    public ReviewUnit generateWordReviewUnit(@RequestParam String groupId, @RequestParam Integer listSize) {
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        WordGroup group = wordService.getGroupById(groupId);
        List<Integer> order = group.getOrder();
        List<String> unitIds = group.getUnitIds() == null ? new ArrayList<String>() : group.getUnitIds();
        // 获取列表间断点
        List<Integer> breaks = group.getBreaks();
        Integer start = breaks.get(breaks.size() - 1);
        //以此为起点获取新的单词序号列表
        List<Integer> wordRanks = new ArrayList<>();
        for (int i = start; i < start + listSize; i++) {
            wordRanks.add(order.get(i));
        }
        //查询出对应单词
//        List<Word> words = wordService.getWordListBywordRanksAndBookId(wordRanks,group.getBookId());
        Document doc = new Document();
        doc.put(group.getBookId(), wordRanks);
        //创建复习单元，内容为众单词序号
        ReviewUnit unit = reviewService.createUnit(new ReviewUnit(user.getId(), 0, doc));
        //更新WordGroup断点和复习单元id列表
        breaks.add(start + listSize);
        unitIds.add(unit.getId());
        wordService.updateGroup(start, groupId, breaks, unitIds);
        return unit;
    }

    @PostMapping("/wordList")
    public List<Word> getWordList(@RequestBody WordListParam param) {
        return wordService.getWordListBywordRanksAndBookId(param.getWordRanks(), param.getBookId());
    }
}
