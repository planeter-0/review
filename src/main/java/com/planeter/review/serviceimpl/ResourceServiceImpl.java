package com.planeter.review.serviceimpl;


import com.planeter.review.model.entity.Resource;
import com.planeter.review.repository.ResourceRepository;
import com.planeter.review.service.ResourceService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author RudeCrab
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceServiceImpl implements ResourceService{
    @Autowired
    ResourceRepository resourceRepository;

    @Override
    public Set<Long> getIdsByUserId(Long userId) {
        return resourceRepository.getIdsByUserId(userId);
    }

    @Override
    public List<Resource> getResourcesByUserId(Long userId) {

        return resourceRepository.getResourcesByUserId(userId);
    }
}