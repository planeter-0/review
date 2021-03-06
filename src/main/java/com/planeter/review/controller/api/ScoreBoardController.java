package com.planeter.review.controller.api;

import com.planeter.review.model.entity.ScoreBoard;
import com.planeter.review.model.entity.UserEntity;
import com.planeter.review.service.ScoreBoardService;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class ScoreBoardController {
    @Resource
    ScoreBoardService scoreBoardService;

    @GetMapping("/board/mine")
    public ScoreBoard getMyScoreBoard() {
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        return scoreBoardService.getScoreBoardByUserId(user.getId());
    }
}
