package com.planeter.review.common.component;

import com.planeter.review.service.ScoreBoardService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ScheduledTask {
    @Resource
    ScoreBoardService scoreBoardService;

    /**
     * 每月初清空计数板
     */
    @Scheduled(cron = "0 0 0 1 * ?")// 每月1日0点
    public void clearBoards() {
        scoreBoardService.clearBoard();
    }
}
