package com.planeter.review.controller.api;


import com.planeter.review.model.entity.Resource;
import com.planeter.review.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 资源
 *
 * @author RudeCrab
 */
@RestController
@RequestMapping("/API/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;
    
    @GetMapping("/list")
    public List<Resource> getList() {
        return resourceService.list();
    }
}
