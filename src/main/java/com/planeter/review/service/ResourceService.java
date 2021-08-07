package com.planeter.review.service;


import com.planeter.review.model.entity.Resource;

import java.util.List;
import java.util.Set;

public interface ResourceService {
    Set<Long> getIdsByUserId(Long id);
    List<Resource> getResourcesByUserId(Long userId);
}
