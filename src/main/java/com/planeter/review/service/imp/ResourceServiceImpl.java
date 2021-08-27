package com.planeter.review.service.imp;


import com.planeter.review.model.entity.Resource;
import com.planeter.review.repository.ResourceRepository;
import com.planeter.review.service.ResourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class ResourceServiceImpl implements ResourceService {
    @javax.annotation.Resource
    private ResourceRepository resourceRepository;


    @Override
    public Set<Long> getIdsByUserId(Long userId) {
        return resourceRepository.selectIdsByUserId(userId);
    }

    @Override
    public void insertResources(Collection<Resource> resources) {
        if (CollectionUtils.isEmpty(resources)) {
            return;
        }
        // 再新增接口类型的资源
        resourceRepository.saveAll(resources);
    }

    @Override
    public void deleteResourceByType(int type) {
        // 先删除所有接口类型的资源
        resourceRepository.deleteByType(1);
    }

    @Override
    public List<Resource> getResourcesByUserId(Long userId) {

        return resourceRepository.getResourcesByUserId(userId);
    }
}