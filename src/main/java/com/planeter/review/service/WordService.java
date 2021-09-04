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

    /**
     * 根据id获取词书背诵计划
     * @param groupId
     * @return
     */
    WordGroup getGroupById(String groupId);

    /**
     * 根据单词序号列表和词书id获取单词列表
     * @param wordRanks
     * @param bookId
     * @return
     */
    List<Word> getWordListBywordRanksAndBookId(List<Integer> wordRanks,String bookId);

    /**
     * 更新词书背诵计划
     * @param start
     * @param groupId
     * @param breaks
     * @param unitIds
     * @return
     */
    WordGroup updateGroup(Integer start, String groupId, List<Integer> breaks, List<String> unitIds);
}
