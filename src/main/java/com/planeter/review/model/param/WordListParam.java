package com.planeter.review.model.param;

import lombok.Data;

import java.util.List;

/**
 * 获取单词列表参数
 */
@Data
public class WordListParam {
    String bookId;
    List<Integer> wordRanks;
}
