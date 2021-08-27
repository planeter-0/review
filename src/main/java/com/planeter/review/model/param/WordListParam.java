package com.planeter.review.model.param;

import lombok.Data;

import java.util.List;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/8/23 6:19
 * @status dev
 */
@Data
public class WordListParam {
    String bookId;
    List<Integer> wordRanks;
}
