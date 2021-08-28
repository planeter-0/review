package com.planeter.review.repository;

import com.planeter.review.model.entity.ScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ScoreBoardRepository extends JpaRepository<ScoreBoard, Long> {
    /**
     * 超时1次, 计分板+1
     */
    @Modifying
    @Query("UPDATE ScoreBoard s SET s.times =s.times+1 WHERE s.uid=:uid")
    @Transactional
    void increaseOne(Long uid);

    /**
     * ScheduledTask,每月初清零计分板
     */
    @Modifying
    @Query(value = "UPDATE score_board  SET times = 0", nativeQuery = true)
    @Transactional
    void clearAll();
}
