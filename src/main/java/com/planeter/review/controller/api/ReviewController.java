package com.planeter.review.controller.api;

import com.planeter.review.model.entity.ReviewUnit;
import com.planeter.review.model.entity.UserEntity;
import com.planeter.review.model.param.CustomUnit;
import com.planeter.review.model.vo.ResultVO;
import com.planeter.review.service.ReviewService;
import com.planeter.review.service.ScoreBoardService;
import org.apache.shiro.SecurityUtils;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ReviewController {
    @Resource
    ReviewService reviewService;
    @Resource
    ScoreBoardService scoreBoardService;

    @PostMapping("/customUnit/create")
    public ReviewUnit createCustomUnit(@RequestBody CustomUnit custom) {
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        Document document = new Document();
        document.put("content", custom.getContent());
        return reviewService.createUnit(new ReviewUnit(custom.getName(), user.getId(), 1, document));
    }

    @PutMapping("/customUnit/update")
    public ResultVO<Object> updateCustomUnit(@RequestParam String id, @RequestParam String record) {
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        // 检查是否超时并更新计数板
        String rec = record.replaceAll("0", "");
        if (rec.length() < 1)
            rec = "0";
        if (rec.charAt(rec.length() - 1) == '2') {
            scoreBoardService.increaseOne(user.getId());
        }
        reviewService.updateRecordById(id, record);
        return new ResultVO<>();
    }

    @GetMapping("/customUnit/{id}")
    public ResultVO<ReviewUnit> getCustomUnitById(@PathVariable String id) {
        ReviewUnit r =reviewService.getById(id);
        return new ResultVO<>(r);
    }

    @GetMapping("/customUnit/mine")
    public ResultVO<List<ReviewUnit>> getMyCustomUnit(@RequestParam(defaultValue = "-1") Integer state) {
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        return new ResultVO<>(reviewService.getByUserIdAndState(user.getId(), state));
    }
}
