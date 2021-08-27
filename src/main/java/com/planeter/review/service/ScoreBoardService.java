package com.planeter.review.service;

import com.planeter.review.model.entity.ScoreBoard;

public interface ScoreBoardService {
    /**
     * 为新用户生成计数板
     *
     * @param userId
     * @return ScoreBoard
     */
    void createScoreBoard(Long userId);

    /**
     * 根据uid返回计数板
     *
     * @param userId
     * @return ScoreBoard
     */
    ScoreBoard getScoreBoardByUserId(Long userId);

    /**
     * 没有按时背诵，计数板+1
     *
     * @param userId
     * @return ScoreBoard
     */
    void increaseOne(Long userId);

    /**
     * 定时任务，每月清空一次计数板
     */
    void clearBoard();
}
