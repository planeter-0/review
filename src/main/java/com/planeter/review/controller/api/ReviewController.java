package com.planeter.review.controller.api;

import com.planeter.review.model.entity.ReviewUnit;
import com.planeter.review.model.entity.UserEntity;
import com.planeter.review.model.param.CustomUnit;
import com.planeter.review.model.vo.ResultVO;
import com.planeter.review.service.ReviewService;
import com.planeter.review.service.ScoreBoardService;
import org.apache.shiro.SecurityUtils;
import org.bson.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/8/10 23:47
 * @status dev
 */
@RestController
public class ReviewController {
    @Resource
    ReviewService reviewService;
    @Resource
    ScoreBoardService scoreBoardService;
    @PostMapping("/customUnit/create")
    public ReviewUnit createCustomUnit(@RequestBody CustomUnit custom){
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        Document document = new Document();
        document.put("content", custom.getContent());
        return reviewService.createUnit(new ReviewUnit(custom.getName(), user.getId(), 1, document));
    }
    @PutMapping("/customUnit/update")
    public ResultVO updateCustomUnit(@RequestParam String id,@RequestParam String record){
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        // 检查是否超时并更新计数板
        String rec =  record.replaceAll("0","");
        if(rec.charAt(rec.length()-1)=='2'){
            scoreBoardService.increaseOne(user.getId());
        }
        reviewService.updateRecordById(id,record);
        return new ResultVO<>();
    }
    @GetMapping("/customUnit/{id}")
    public ResultVO getCustomUnitById(@PathVariable String id){
        return new ResultVO<>(reviewService.getById(id));
    }
    @GetMapping("/customUnit/mine")
    @Cacheable(value = "unit",key="")
    public ResultVO getMyCustomUnit(@RequestParam(defaultValue="-1") Integer state){
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        return new ResultVO<>(reviewService.getByUserIdAndState(user.getId(),state));
    }
}
