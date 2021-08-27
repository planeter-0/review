package com.planeter.review.service.imp;

import com.planeter.review.model.entity.ScoreBoard;
import com.planeter.review.repository.ScoreBoardRepository;
import com.planeter.review.service.ScoreBoardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户超时次数记录板相关服务
 */
@Service
public class ScoreBoardServiceImp implements ScoreBoardService {
    @Resource
    ScoreBoardRepository scoreBoardRepository;

    @Override
    public void createScoreBoard(Long userId) {
        scoreBoardRepository.save(new ScoreBoard(userId));
    }

    @Override
    public ScoreBoard getScoreBoardByUserId(Long userId) {
        return scoreBoardRepository.getById(userId);
    }

    @Override
    public void increaseOne(Long userId) {
        scoreBoardRepository.increaseOne(userId);
    }

    @Override
    public void clearBoard() {
        scoreBoardRepository.clearAll();
    }
}
