package com.planeter.review.service;

import com.planeter.review.model.entity.ReviewUnit;

import java.util.List;

public interface ReviewService {
    /**
     * 创建复习单元
     * @param reviewUnit
     */
    ReviewUnit createUnit(ReviewUnit reviewUnit);

    /**
     * 删除复习单元
     * @param id
     */
    void deleteUnit(String id);

    /**
     * 更新复习单元记录
     * @param id
     * @param record
     */
    void updateRecordById(String id,String record);

    /**
     * 根据id获取复习单元
     * @param id
     * @return
     */
    ReviewUnit getById(String id);

    /**
     * 根据用户id获取复习单元列表
     * @param id
     * @return
     */
    List<ReviewUnit> getByUserIdAndState(Long id, Integer state);
}
