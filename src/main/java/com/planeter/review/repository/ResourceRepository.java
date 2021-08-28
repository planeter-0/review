package com.planeter.review.repository;

import com.planeter.review.model.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ResourceRepository extends JpaRepository<Resource,Long> {
//    @Query(value = "select r.id from Resource r where r.userId=?1")
//    Set<Long> selectIdsByUserId(Long userId);
//    @Query(value = "select r from Resource r where r.userId=?1")
//    List<Resource> getResourcesByUserId(Long userId);

    void deleteByType(Integer type);
}
