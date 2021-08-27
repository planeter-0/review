package com.planeter.review.service;

import com.planeter.review.model.entity.Word;
import com.planeter.review.model.entity.WordGroup;

import java.util.List;

public interface WordService {
    /**
     * 创建并一个词书背诵计划
     * @param userId 用户id
     * @param bookId 词书id
     * @param total 词书总词数
     * @return
     */
    WordGroup createGroup(Long userId, String bookId, Integer total);

    WordGroup getGroupById(String groupId);
    List<Word> getWordListBywordRanksAndBookId(List<Integer> wordRanks,String bookId);

    void updateGroup(Integer start, String groupId,List<Integer> breaks,List<String> unitIds);
}
